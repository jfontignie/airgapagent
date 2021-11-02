package com.airgap.airgapagent;

import com.airgap.airgapagent.configuration.FileCopyAction;
import com.airgap.airgapagent.configuration.FileSearchAction;
import com.airgap.airgapagent.service.FileCrawlService;
import com.airgap.airgapagent.service.FileScanService;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * com.airgap.airgapagent
 * Created by Jacques Fontignie on 5/30/2020.
 */
@SuppressWarnings("java:S106")
@Component
public class CommandLineApplicationRunner implements CommandLineRunner {
    private static final Logger log =
            LoggerFactory.getLogger(CommandLineApplicationRunner.class);
    private final FileScanService fileScanService;
    private final FileCrawlService fileCrawlService;

    @SuppressWarnings("FieldCanBeLocal")
    @Parameter(names = "-help", help = true)
    private boolean help = false;

    public CommandLineApplicationRunner(
            FileCrawlService fileCrawlService,
            FileScanService fileScanService
    ) {
        this.fileCrawlService = fileCrawlService;
        this.fileScanService = fileScanService;
    }


    @Override
    public void run(String... args) throws Exception {

        help = false;
        FileCopyAction fileCopyAction = new FileCopyAction();
        FileSearchAction fileSearchAction = new FileSearchAction();

        JCommander commander = JCommander.newBuilder()
                .addObject(this)
                .addCommand(fileCopyAction)
                .addCommand(fileSearchAction)
                .build();
        try {
            commander.parse(args);
        } catch (ParameterException e) {
            System.err.println(e.getMessage());
            printHelp(commander);
            return;
        }
        if (help) {
            printHelp(commander);
            return;
        }

        if (commander.getParsedCommand() == null) {
            System.err.println("No command specified");
            printHelp(commander);
            return;
        }
        if (commander.getParsedCommand().equals(FileSearchAction.COMMAND_NAME)) {

            long found = fileScanService.scanFolder(fileSearchAction, fileCrawlService);
            log.info("Number or elements found: {}", found);
        }

        if (commander.getParsedCommand().equals(FileCopyAction.COMMAND_NAME)) {
            long found = fileScanService.copyFolder(fileCopyAction, fileCrawlService);
            log.info("Number or elements found: {}", found);
        }
    }

    private void printHelp(JCommander commander) {
        StringBuilder stringBuilder = new StringBuilder();
        commander.getUsageFormatter().usage(stringBuilder, "\t");
        System.err.println(stringBuilder);
    }

}
