package com.airgap.airgapagent.service;

import com.airgap.airgapagent.domain.ExactMatchContext;
import com.airgap.airgapagent.utils.FileStateConverter;
import com.airgap.airgapagent.utils.StateConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

/**
 * com.airgap.airgapagent.service
 * Created by Jacques Fontignie on 5/30/2020.
 */
@Service
public class FileScanService {

    private static final Logger log = LoggerFactory.getLogger(FileScanService.class);

    private static final StateConverter<File> STATE_CONVERTER = FileStateConverter.of();

    private final ExactMatchService exactMatchService;

    public FileScanService(
            ExactMatchService exactMatchService) {
        this.exactMatchService = exactMatchService;
    }

    public void scanFolder(ExactMatchContext<File> exactMatchContext, FileCrawlService crawlService) throws IOException {
        log.info("Staring scan");
        File scanFolder = exactMatchContext.getRoot();
        if (!scanFolder.exists()) {
            throw new IllegalStateException("Folder " + scanFolder + " does not exist");
        }

        exactMatchService.scan(
                exactMatchContext,
                crawlService,
                STATE_CONVERTER
        );
        log.info("Scan finished");
    }
}
