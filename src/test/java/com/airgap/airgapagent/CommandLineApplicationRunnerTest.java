package com.airgap.airgapagent;

import com.airgap.airgapagent.service.file.FileCrawlService;
import com.airgap.airgapagent.service.file.FileScanService;
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
        FileScanService fileScanService = Mockito.mock(FileScanService.class);
        Mockito.doReturn(5L).when(fileScanService).scanFolder(Mockito.any(), Mockito.any());
        CommandLineApplicationRunner runner = new CommandLineApplicationRunner(crawler,
                fileScanService);

        runner.run(
                ("search -minHit 5 -folder src/test/resources/sample " +
                        "-found target/search.csv " +
                        "-corpus src/test/resources/sample/bigsample.csv").split(" "));
        Assertions.assertTrue(true);
    }
}
