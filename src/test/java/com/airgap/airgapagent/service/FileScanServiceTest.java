package com.airgap.airgapagent.service;

import com.airgap.airgapagent.domain.ExactMatchContext;
import com.airgap.airgapagent.domain.ExactMatchContextBuilder;
import com.airgap.airgapagent.utils.ConstantsTest;
import org.apache.commons.io.FileUtils;
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

    private static final int EXPECTED = 11;
    private static final int MIN_HIT = 1;
    private FileScanService fileScanService;
    private FileCrawlService fileCrawlService;

    @BeforeEach
    public void setUp() throws IOException {
        MockEnvironment environment = new MockEnvironment()
                .withProperty(ErrorServiceImpl.ERROR_FILE, "target/error.dat")
                .withProperty(SyslogService.SYSLOG_SERVER, "127.0.0.1");

        ErrorServiceImpl errorService = new ErrorServiceImpl(environment);

        fileScanService = new FileScanService(
                new ExactMatchService(
                        new VisitorService(),
                        new CorpusBuilderService(),
                        errorService,
                        new SyslogService(environment)),
                errorService
        );

        fileCrawlService = new FileCrawlService(new ContentReaderService());
    }

    @Test
    void run() throws IOException {
        ExactMatchContext<File> context = new ExactMatchContextBuilder<File>()
                .setRoot(ConstantsTest.SAMPLE_FOLDER)
                .setExactMatchFile(new File(ConstantsTest.CORPUS_SAMPLE_STRING))
                .setFoundFile(new File("target/run_found.csv"))
                .setStateFile(new File("target/run_sate.dat"))
                .setMinHit(MIN_HIT)
                .setSaveInterval(Duration.ofSeconds(5))
                .createExactMatchContext();
        long count = fileScanService.scanFolder(context, fileCrawlService);
        Assertions.assertTrue(true);
        Assertions.assertEquals(EXPECTED, count);
    }

    @Test
    void copy() throws IOException {

        File destination = new File("target/copyTest");
        FileUtils.deleteDirectory(destination);
        ExactMatchContext<File> context = new ExactMatchContextBuilder<File>()
                .setRoot(ConstantsTest.SAMPLE_FOLDER)
                .setExactMatchFile(new File(ConstantsTest.CORPUS_SAMPLE_STRING))
                .setFoundFile(new File("target/run_found.csv"))
                .setStateFile(new File("target/run_sate.dat"))
                .setMinHit(MIN_HIT)
                .setSaveInterval(Duration.ofSeconds(5))
                .createExactMatchContext();
        long count = fileScanService.copyFolder(context, fileCrawlService, destination);
        Assertions.assertTrue(destination.exists());
        FileUtils.deleteDirectory(destination);
        Assertions.assertEquals(EXPECTED, count);
    }

    @Disabled("Performance test")
    @Test
    void testLong() throws IOException {
        ExactMatchContext<File> context = new ExactMatchContextBuilder<File>()
                .setRoot(new File("c:/"))
                .setExactMatchFile(new File(ConstantsTest.CORPUS_SAMPLE_STRING))
                .setFoundFile(new File("target/testLong_found.csv"))
                .setStateFile(new File("target/testLong_state.dat"))
                .setMinHit(50)
                .setSaveInterval(Duration.ofSeconds(1))
                .createExactMatchContext();
        fileScanService.scanFolder(context, fileCrawlService);
        Assertions.assertTrue(true);
    }
}
