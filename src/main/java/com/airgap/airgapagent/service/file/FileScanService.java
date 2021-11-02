package com.airgap.airgapagent.service.file;

import com.airgap.airgapagent.configuration.CopyOption;
import com.airgap.airgapagent.configuration.FileCopyConfiguration;
import com.airgap.airgapagent.configuration.FileSearchConfiguration;
import com.airgap.airgapagent.service.ErrorService;
import com.airgap.airgapagent.service.SearchEngine;
import com.airgap.airgapagent.utils.StateConverter;
import com.airgap.airgapagent.utils.file.FileStateConverter;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;

/**
 * com.airgap.airgapagent.service
 * Created by Jacques Fontignie on 5/30/2020.
 */
@Service
public class FileScanService {

    private static final Logger log = LoggerFactory.getLogger(FileScanService.class);

    private static final StateConverter<File> STATE_CONVERTER = FileStateConverter.of();

    private final SearchEngine searchEngine;

    private final ErrorService errorService;

    public FileScanService(
            SearchEngine searchEngine,
            ErrorService errorService) {
        this.searchEngine = searchEngine;
        this.errorService = errorService;
    }


    public long copyFolder(FileCopyConfiguration fileCopyConfiguration, FileCrawlService crawlService) throws IOException {
        File scanFolder = fileCopyConfiguration.getRootLocation();
        if (!scanFolder.exists()) {
            throw new IllegalStateException("Folder " + scanFolder + " does not exist");
        }
        if (fileCopyConfiguration.getCopyOptions().contains(CopyOption.CLEAN_ON_STARTUP)) {
            FileUtils.deleteDirectory(scanFolder);
            Files.createDirectory(scanFolder.toPath());
        }
        Long count = searchEngine.buildScan(
                        fileCopyConfiguration,
                        crawlService,
                        STATE_CONVERTER)
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

    public long scanFolder(FileSearchConfiguration fileSearchConfiguration, FileCrawlService crawlService) throws IOException {

        log.info("Staring scan");
        File scanFolder = fileSearchConfiguration.getRootLocation();
        if (!scanFolder.exists()) {
            throw new IllegalStateException("Folder " + scanFolder + " does not exist");
        }

        long count = searchEngine.scan(
                fileSearchConfiguration,
                crawlService,
                STATE_CONVERTER
        );
        log.info("Scan finished");
        return count;

    }
}
