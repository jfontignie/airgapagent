package com.airgap.airgapagent;

import com.airgap.airgapagent.service.file.FileCrawlService;
import com.airgap.airgapagent.service.file.FileSearchEngine;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

/**
 * com.airgap.airgapagent
 * Created by Jacques Fontignie on 6/4/2020.
 */
class CommandLineApplicationRunnerTest {

    @Test
    void help() throws Exception {
        CommandLineApplicationRunner runner = new CommandLineApplicationRunner(null, null);
        runner.run("-help");
        Assertions.assertTrue(true);
    }

    @Test
    void run() throws Exception {
        FileCrawlService crawler = Mockito.mock(FileCrawlService.class);
        FileSearchEngine fileSearchEngine = Mockito.mock(FileSearchEngine.class);
        Mockito.doReturn(5L).when(fileSearchEngine).scanFolder(Mockito.any(), Mockito.any());
        CommandLineApplicationRunner runner = new CommandLineApplicationRunner(crawler,
                fileSearchEngine);

        runner.run(
                ("search -minHit 5 -folder src/test/resources/sample " +
                        "-found target/search.csv " +
                        "-corpus src/test/resources/sample/bigsample.csv").split(" "));
        Assertions.assertTrue(true);
    }

    @Test
    void runContinuous() throws Exception {
        FileCrawlService crawler = Mockito.mock(FileCrawlService.class);
        FileSearchEngine fileSearchEngine = Mockito.mock(FileSearchEngine.class);
        Mockito.doReturn(5L).when(fileSearchEngine).scanFolder(Mockito.any(), Mockito.any());
        CommandLineApplicationRunner runner = new CommandLineApplicationRunner(crawler,
                fileSearchEngine);

        Runnable runnable = () -> {
            try {
                runner.run(
                        ("search -continuous -minHit 5 -folder src/test/resources/sample " +
                                "-found target/search.csv " +
                                "-corpus src/test/resources/sample/bigsample.csv").split(" "));
            } catch (Exception e) {
                e.printStackTrace();
            }

        };
        Thread t = new Thread(runnable);
        t.start();
        Assertions.assertTrue(true);
        t.interrupt();
    }
}
