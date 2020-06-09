package com.airgap.airgapagent.service;

import com.airgap.airgapagent.configuration.FileCopyAction;
import com.airgap.airgapagent.configuration.FileSearchAction;
import com.airgap.airgapagent.utils.FileStateConverter;
import com.airgap.airgapagent.utils.StateConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
 * com.airgap.airgapagent.service
 * Created by Jacques Fontignie on 5/30/2020.
 */
@Service
public class FileScanService {

    private static final Logger log = LoggerFactory.getLogger(FileScanService.class);

    private static final StateConverter<File> STATE_CONVERTER = FileStateConverter.of();

    private final ExactMatchService exactMatchService;

    private final ErrorService errorService;

    public FileScanService(
            ExactMatchService exactMatchService,
            ErrorService errorService) {
        this.exactMatchService = exactMatchService;
        this.errorService = errorService;
    }


    public long copyFolder(FileCopyAction fileCopyAction, FileCrawlService crawlService) throws IOException {
        File scanFolder = fileCopyAction.getRootLocation();
        if (!scanFolder.exists()) {
            throw new IllegalStateException("Folder " + scanFolder + " does not exist");
        }
        Long count = exactMatchService.buildScan(
                fileCopyAction,
                crawlService,
                STATE_CONVERTER)
                .flatMap(exactMatchingResult -> {
                    try {
                        //noinspection BlockingMethodInNonBlockingContext
                        crawlService.copy(scanFolder,
                                exactMatchingResult.getDataSource().getSource(), fileCopyAction.getTarget());
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

    public long scanFolder(FileSearchAction fileSearchAction, FileCrawlService crawlService) throws IOException {

        log.info("Staring scan");
        File scanFolder = fileSearchAction.getRootLocation();
        if (!scanFolder.exists()) {
            throw new IllegalStateException("Folder " + scanFolder + " does not exist");
        }

        long count = exactMatchService.scan(
                fileSearchAction,
                crawlService,
                STATE_CONVERTER
        );
        log.info("Scan finished");
        return count;

    }
}
