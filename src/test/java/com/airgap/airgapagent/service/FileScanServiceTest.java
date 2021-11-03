package com.airgap.airgapagent.service;

import com.airgap.airgapagent.configuration.FileCopyConfiguration;
import com.airgap.airgapagent.configuration.FileSearchConfiguration;
import com.airgap.airgapagent.service.file.FileCrawlService;
import com.airgap.airgapagent.service.file.FileSearchEngine;
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
import java.util.Calendar;

/**
 * com.airgap.airgapagent.service
 * Created by Jacques Fontignie on 5/30/2020.
 */
class FileScanServiceTest {

    private static final int EXPECTED = 11;
    private static final int MIN_HIT = 1;
    private FileSearchEngine fileSearchEngine;
    private FileCrawlService fileCrawlService;

    @BeforeEach
    public void setUp() throws IOException {
        MockEnvironment environment = new MockEnvironment()
                .withProperty(ErrorServiceImpl.ERROR_FILE, "target/error.dat")
                .withProperty(SyslogService.SYSLOG_SERVER, "127.0.0.1");

        ErrorServiceImpl errorService = new ErrorServiceImpl(environment);

        fileSearchEngine = new FileSearchEngine(
                new SearchEngine(),
                errorService
        );

        fileCrawlService = new FileCrawlService(new ContentReaderService(),
                errorService);
    }

    @Test
    void run() throws IOException {

        FileSearchConfiguration action = new FileSearchConfiguration();
        action.setRootLocation(ConstantsTest.SAMPLE_FOLDER);
        action.setCorpus(new File(ConstantsTest.CORPUS_SAMPLE_STRING));
        action.setFoundLocation(new File("target/run_found.csv"));
        action.setStateLocation(new File("target/run_sate.dat"));
        action.setMinHit(MIN_HIT);
        action.setInterval(5);

        long count = fileSearchEngine.scanFolder(action, fileCrawlService);
        Assertions.assertTrue(true);
        Assertions.assertEquals(EXPECTED, count);
    }

    @Test
    void copy() throws IOException {

        File destination = new File("target/copyTest");
        FileUtils.deleteDirectory(destination);
        FileCopyConfiguration action = new FileCopyConfiguration();
        action.setRoot(ConstantsTest.SAMPLE_FOLDER);
        action.setCorpus(new File(ConstantsTest.CORPUS_SAMPLE_STRING));
        action.setStateLocation(new File("target/run_state.dat"));
        action.setMinHit(MIN_HIT);
        action.setInterval(5);
        action.setTarget(destination);

        long count = fileSearchEngine.copyFolder(action, fileCrawlService);
        Assertions.assertTrue(destination.exists());
        FileUtils.deleteDirectory(destination);
        Assertions.assertEquals(EXPECTED, count);
    }

    @Disabled("Performance test")
    @Test
    void testLong() throws IOException {

        FileSearchConfiguration action = new FileSearchConfiguration();
        action.setRootLocation(new File("c:/"));
        action.setCorpus(new File(ConstantsTest.CORPUS_SAMPLE_STRING));
        action.setFoundLocation(new File("target/found_long.csv"));
        action.setStateLocation(new File("target/state_long.dat"));
        action.setMinHit(MIN_HIT);
        action.setInterval(5);

        fileSearchEngine.scanFolder(action, fileCrawlService);
        Assertions.assertTrue(true);
    }


    @Disabled("Performance test earlier than one day")
    @Test
    void testRecent() throws IOException {

        FileSearchConfiguration action = new FileSearchConfiguration();
        action.setRootLocation(new File("c:/"));
        action.setCorpus(new File(ConstantsTest.CORPUS_SAMPLE_STRING));
        action.setFoundLocation(new File("target/run_found.csv"));
        action.setStateLocation(new File("target/run_sate.dat"));

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);

        action.setLaterThan(calendar.getTime());
        action.setMinHit(MIN_HIT);
        action.setInterval(5);

        fileSearchEngine.scanFolder(action, fileCrawlService);
        Assertions.assertTrue(true);
    }
}
