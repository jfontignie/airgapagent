package com.airgap.airgapagent.service;

import com.airgap.airgapagent.domain.ExactMatchContext;
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
                new FileWalkerService(), new AhoCorasickMatcherService(),
                new CorpusBuilderService(),
                new ContentReaderService());
        ExactMatchContext context = new ExactMatchContext(ConstantsTest.SAMPLE_FOLDER,
                new File(ConstantsTest.CORPUS_SAMPLE_STRING),
                new File("target/run_found.csv"),
                new File("target/run_sate.dat"),
                new File("target/run_error.csv"),
                50, 100,
                Duration.ofSeconds(5));
        fileScanService.run(context);
        Assertions.assertTrue(true);
    }

    @Disabled("Performance test")
    @Test
    void testLong() throws IOException {
        FileScanService fileScanService = new FileScanService(
                new FileWalkerService(), new AhoCorasickMatcherService(),
                new CorpusBuilderService(),
                new ContentReaderService());
        ExactMatchContext context = new ExactMatchContext(new File("c:/projects"),
                new File(ConstantsTest.CORPUS_SAMPLE_STRING),
                new File("target/testLong_found.csv"),
                new File("target/testLong_state.dat"),
                new File("target/testLong_error.csv"),
                50, 100,
                Duration.ofSeconds(5));
        fileScanService.run(context);
        Assertions.assertTrue(true);
    }
}
