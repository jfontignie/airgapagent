package com.airgap.airgapagent.service;

import com.airgap.airgapagent.batch.ExactMatchBatchConfiguration;
import com.airgap.airgapagent.domain.ExactMatchContext;
import com.airgap.airgapagent.domain.ExactMatchContextBuilder;
import com.airgap.airgapagent.utils.ConstantsTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.mock.env.MockEnvironment;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

/**
 * com.airgap.airgapagent.service
 * Created by Jacques Fontignie on 5/30/2020.
 */
class FileScanServiceTest {

    private FileScanService fileScanService;
    private FileCrawlService fileCrawlService;

    @BeforeEach
    public void setUp() throws IOException {
        MockEnvironment environment = new MockEnvironment().withProperty(ExactMatchBatchConfiguration.MATCH_ERROR_FILE, "target/error.dat");

        ErrorServiceImpl errorService = new ErrorServiceImpl(environment);

        fileScanService = new FileScanService(
                new VisitorService(),
                new CorpusBuilderService(),
                errorService);

        fileCrawlService = new FileCrawlService(new ContentReaderService(errorService));
    }

    @Test
    void run() throws IOException {
        ExactMatchContext context = new ExactMatchContextBuilder()
                .setScanFolder(ConstantsTest.SAMPLE_FOLDER)
                .setExactMatchFile(new File(ConstantsTest.CORPUS_SAMPLE_STRING))
                .setFoundFile(new File("target/run_found.csv"))
                .setStateFile(new File("target/run_sate.dat"))
                .setMinHit(50)
                .setMaxHit(100)
                .setSaveInterval(Duration.ofSeconds(5))
                .createExactMatchContext();
        fileScanService.run(context, fileCrawlService);
        Assertions.assertTrue(true);
    }

    @Disabled("Performance test")
    @Test
    void testLong() throws IOException {
        ExactMatchContext context = new ExactMatchContextBuilder()
                .setScanFolder(new File("c:/"))
                .setExactMatchFile(new File(ConstantsTest.CORPUS_SAMPLE_STRING))
                .setFoundFile(new File("target/testLong_found.csv"))
                .setStateFile(new File("target/testLong_state.dat"))
                .setMinHit(50)
                .setMaxHit(100)
                .setSaveInterval(Duration.ofSeconds(1))
                .createExactMatchContext();
        fileScanService.run(context, fileCrawlService);
        Assertions.assertTrue(true);
    }
}
