package com.airgap.airgapagent;

import com.airgap.airgapagent.domain.ExactMatchContext;
import com.airgap.airgapagent.service.FileScanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * com.airgap.airgapagent
 * Created by Jacques Fontignie on 5/30/2020.
 */
@Component
public class CommandLineApplicationRunner implements ApplicationRunner {
    private static final Logger log =
            LoggerFactory.getLogger(CommandLineApplicationRunner.class);
    private final FileScanService fileScanService;
    private final ExactMatchContext exactMatchContext;

    public CommandLineApplicationRunner(FileScanService fileScanService,
                                        ExactMatchContext exactMatchContext
    ) {
        this.fileScanService = fileScanService;
        this.exactMatchContext = exactMatchContext;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("Ready to analyse folder");
        fileScanService.run(exactMatchContext);
        log.info("Analysis finished");

    }
}
