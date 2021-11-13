package com.airgap.airgapagent;

import com.airgap.airgapagent.configuration.AbstractScanConfiguration;
import com.airgap.airgapagent.configuration.FileCopyConfiguration;
import com.airgap.airgapagent.configuration.FileSearchConfiguration;
import com.airgap.airgapagent.service.file.FileCrawlService;
import com.airgap.airgapagent.service.file.FileSearchEngine;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.function.Consumer;

/**
 * com.airgap.airgapagent
 * Created by Jacques Fontignie on 5/30/2020.
 */
@SuppressWarnings("java:S106")
@Component
public class CommandLineApplicationRunner implements CommandLineRunner {
    private static final Logger log =
            LoggerFactory.getLogger(CommandLineApplicationRunner.class);
    private final FileSearchEngine fileSearchEngine;
    private final FileCrawlService fileCrawlService;

    @SuppressWarnings("FieldCanBeLocal")
    @Parameter(names = "-help", help = true)
    private boolean help = false;

    public CommandLineApplicationRunner(
            FileCrawlService fileCrawlService,
            FileSearchEngine fileSearchEngine
    ) {
        this.fileCrawlService = fileCrawlService;
        this.fileSearchEngine = fileSearchEngine;
    }


    @Override
    public void run(String... args) throws Exception {

        help = false;
        FileCopyConfiguration fileCopyAction = new FileCopyConfiguration();
        FileSearchConfiguration fileSearchAction = new FileSearchConfiguration();

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

        AbstractScanConfiguration<File> configuration = null;
        Consumer<AbstractScanConfiguration<File>> action = null;

        if (commander.getParsedCommand().equals(FileSearchConfiguration.COMMAND_NAME)) {
            configuration = fileSearchAction;
            action = config -> {
                long found;
                try {
                    found = fileSearchEngine.scanFolder(fileSearchAction, fileCrawlService);
                } catch (IOException e) {
                    throw new IllegalStateException(e);
                }
                log.info("Number or elements found: {}", found);
            };
        }


        if (commander.getParsedCommand().equals(FileCopyConfiguration.COMMAND_NAME)) {
            configuration = fileCopyAction;
            action = config -> {
                long found;
                try {
                    found = fileSearchEngine.copyFolder(fileCopyAction, fileCrawlService);
                } catch (IOException e) {
                    throw new IllegalStateException(e);
                }
                log.info("Number or elements found: {}", found);
            };
        }

        if (configuration == null) {
            throw new IllegalStateException("Action has not been set");
        }

        Runner<File> runner = configuration.isContinuous() ?
                new ContinuousRunner<>() : new SimpleRunner<>();
        runner.run(configuration, action);

    }

    private void printHelp(JCommander commander) {
        StringBuilder stringBuilder = new StringBuilder();
        commander.getUsageFormatter().usage(stringBuilder, "\t");
        System.err.println(stringBuilder);
    }

}
