package com.airgap.airgapagent.service;

import com.airgap.airgapagent.configuration.FileCopyAction;
import com.airgap.airgapagent.configuration.FileSearchAction;
import com.airgap.airgapagent.service.syslog.SyslogService;
import com.airgap.airgapagent.utils.ConstantsTest;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.mock.env.MockEnvironment;

import java.io.File;
import java.io.IOException;

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
                        new SyslogService(environment),
                        new MatcherService()),
                errorService
        );

        fileCrawlService = new FileCrawlService(new ContentReaderService(),
                errorService);
    }

    @Test
    void run() throws IOException {

        FileSearchAction action = new FileSearchAction();
        action.setRootLocation(ConstantsTest.SAMPLE_FOLDER);
        action.setCorpus(new File(ConstantsTest.CORPUS_SAMPLE_STRING));
        action.setFoundLocation(new File("target/run_found.csv"));
        action.setStateLocation(new File("target/run_sate.dat"));
        action.setMinHit(MIN_HIT);
        action.setInterval(5);

        long count = fileScanService.scanFolder(action, fileCrawlService);
        Assertions.assertTrue(true);
        Assertions.assertEquals(EXPECTED, count);
    }

    @Test
    void copy() throws IOException {

        File destination = new File("target/copyTest");
        FileUtils.deleteDirectory(destination);
        FileCopyAction action = new FileCopyAction();
        action.setRoot(ConstantsTest.SAMPLE_FOLDER);
        action.setCorpus(new File(ConstantsTest.CORPUS_SAMPLE_STRING));
        action.setStateLocation(new File("target/run_state.dat"));
        action.setMinHit(MIN_HIT);
        action.setInterval(5);
        action.setTarget(destination);

        long count = fileScanService.copyFolder(action, fileCrawlService);
        Assertions.assertTrue(destination.exists());
        FileUtils.deleteDirectory(destination);
        Assertions.assertEquals(EXPECTED, count);
    }

    @Disabled("Performance test")
    @Test
    void testLong() throws IOException {

        FileSearchAction action = new FileSearchAction();
        action.setRootLocation(new File("c:/"));
        action.setCorpus(new File(ConstantsTest.CORPUS_SAMPLE_STRING));
        action.setFoundLocation(new File("target/run_found.csv"));
        action.setStateLocation(new File("target/run_sate.dat"));
        action.setMinHit(MIN_HIT);
        action.setInterval(5);

        fileScanService.scanFolder(action, fileCrawlService);
        Assertions.assertTrue(true);
    }
}
