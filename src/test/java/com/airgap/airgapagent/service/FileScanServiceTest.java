package com.airgap.airgapagent.service;

import com.airgap.airgapagent.domain.ExactMatchContext;
import com.airgap.airgapagent.domain.ExactMatchContextBuilder;
import com.airgap.airgapagent.utils.ConstantsTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

/**
 * com.airgap.airgapagent.service
 * Created by Jacques Fontignie on 5/30/2020.
 */
class FileScanServiceTest {

    @Test
    void run() throws IOException {
        FileScanService fileScanService = new FileScanService(
                new FileWalkerService(),
                new CorpusBuilderService(),
                new ContentReaderService());
        ExactMatchContext context = new ExactMatchContextBuilder()
                .setScanFolder(ConstantsTest.SAMPLE_FOLDER)
                .setExactMatchFile(new File(ConstantsTest.CORPUS_SAMPLE_STRING))
                .setFoundFile(new File("target/run_found.csv"))
                .setStateFile(new File("target/run_sate.dat"))
                .setErrorFile(new File("target/run_error.csv"))
                .setMinHit(50)
                .setMaxHit(100)
                .setSaveInterval(Duration.ofSeconds(5))
                .createExactMatchContext();
        fileScanService.run(context);
        Assertions.assertTrue(true);
    }

    @Disabled("Performance test")
    @Test
    void testLong() throws IOException {
        FileScanService fileScanService = new FileScanService(
                new FileWalkerService(),
                new CorpusBuilderService(),
                new ContentReaderService());
        ExactMatchContext context = new ExactMatchContextBuilder()
                .setScanFolder(new File("c:/projects"))
                .setExactMatchFile(new File(ConstantsTest.CORPUS_SAMPLE_STRING))
                .setFoundFile(new File("target/testLong_found.csv"))
                .setStateFile(new File("target/testLong_state.dat"))
                .setErrorFile(new File("target/testLong_error.csv"))
                .setMinHit(50)
                .setMaxHit(100)
                .setSaveInterval(Duration.ofSeconds(5))
                .createExactMatchContext();
        fileScanService.run(context);
        Assertions.assertTrue(true);
    }
}
