package com.airgap.airgapagent.service.file;

import com.airgap.airgapagent.algo.SearchAlgorithm;
import com.airgap.airgapagent.algo.SearchAlgorithmFactory;
import com.airgap.airgapagent.algo.SearchOption;
import com.airgap.airgapagent.configuration.AbstractScanConfiguration;
import com.airgap.airgapagent.configuration.CopyOption;
import com.airgap.airgapagent.configuration.FileCopyConfiguration;
import com.airgap.airgapagent.configuration.FileSearchConfiguration;
import com.airgap.airgapagent.service.CorpusBuilderService;
import com.airgap.airgapagent.service.ErrorService;
import com.airgap.airgapagent.service.SearchContext;
import com.airgap.airgapagent.service.SearchEngine;
import com.airgap.airgapagent.utils.CrawlState;
import com.airgap.airgapagent.utils.file.FileSerializer;
import com.airgap.airgapagent.utils.filters.LaterThanFileVisitorFilter;
import com.airgap.airgapagent.utils.filters.ListVisitorFilter;
import com.airgap.airgapagent.utils.visitor.CSVWriterEventListener;
import com.airgap.airgapagent.utils.visitor.CountFoundListener;
import com.airgap.airgapagent.utils.visitor.PersistentStateListener;
import com.airgap.airgapagent.utils.visitor.ProgressLogStateListener;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;
import java.util.Set;

/**
 * com.airgap.airgapagent.service
 * Created by Jacques Fontignie on 5/30/2020.
 */
@Service
public class FileSearchEngine {

    private static final Logger log = LoggerFactory.getLogger(FileSearchEngine.class);


    private final SearchEngine searchEngine;

    private final ErrorService errorService;

    public FileSearchEngine(
            SearchEngine searchEngine,
            ErrorService errorService) {
        this.searchEngine = searchEngine;
        this.errorService = errorService;
    }


    public long copyFolder(
            FileCopyConfiguration fileCopyConfiguration, FileCrawlService crawlService) throws IOException {
        File scanFolder = fileCopyConfiguration.getRootLocation();
        if (!scanFolder.exists()) {
            throw new IllegalStateException("Folder " + scanFolder + " does not exist");
        }
        if (fileCopyConfiguration.getCopyOptions().contains(CopyOption.CLEAN_ON_STARTUP)) {
            FileUtils.deleteDirectory(scanFolder);
            Files.createDirectory(scanFolder.toPath());
        }

        SearchContext<File> context = buildContext(fileCopyConfiguration, crawlService);

        Long count = searchEngine.buildScan(context)
                .flatMap(exactMatchingResult -> {
                    try {
                        //noinspection BlockingMethodInNonBlockingContext
                        crawlService.copy(scanFolder,
                                exactMatchingResult.getDataSource().getSource(),
                                fileCopyConfiguration.getTarget(),
                                fileCopyConfiguration.getCopyOptions());
                        return Flux.just(exactMatchingResult);
                    } catch (IOException e) {
                        errorService.error(exactMatchingResult.getDataSource(), "Impossible to copy file", e);
                        return Flux.empty();
                    }
                }).count()
                //Wait the last one has been handler
                .block();

        log.info("Operation finished. Files found: {}", count);
        return Objects.requireNonNullElse(count, 0L);
    }

    public long scanFolder(FileSearchConfiguration configuration, FileCrawlService crawlService) throws IOException {

        log.info("Staring scan");
        File scanFolder = configuration.getRootLocation();
        if (!scanFolder.exists()) {
            throw new IllegalStateException("Folder " + scanFolder + " does not exist");
        }

        SearchContext<File> context = buildContext(configuration, crawlService);

        context.addListener(new CSVWriterEventListener<>(configuration.getFoundLocation(), configuration.getExclude(), FileSerializer.of()));

        long count = searchEngine.scan(context);
        log.info("Scan finished");
        return count;

    }

    private SearchContext<File> buildContext(AbstractScanConfiguration<File> configuration, FileCrawlService crawlService) throws IOException {
        ListVisitorFilter<File> visitorFilter = new ListVisitorFilter<>();
        if (configuration.getLaterThan() != null) {
            visitorFilter.addFilter(new LaterThanFileVisitorFilter(configuration.getLaterThan()));
        }

        SearchAlgorithm searchAlgorithm = SearchAlgorithmFactory.getMatcher(configuration.getAlgo(),
                Set.of(SearchOption.CASE_INSENSITIVE),
                CorpusBuilderService.build(configuration.getCorpusLocation()));

        SearchContext<File> searchContext = new SearchContext<>(
                visitorFilter,
                crawlService,
                searchAlgorithm,
                configuration,
                CrawlState.of(configuration.getRootLocation())
        );

        searchContext.addListener(new PersistentStateListener<>(5, configuration.getStateLocation(), FileSerializer.of()));
        searchContext.addListener(new CountFoundListener<>(5));
        searchContext.addListener(new ProgressLogStateListener<>(5));
        return searchContext;
    }
}
