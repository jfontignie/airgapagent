package com.airgap.airgapagent;

import com.airgap.airgapagent.configuration.FileSearchConfiguration;
import com.airgap.airgapagent.service.ContentReaderService;
import com.airgap.airgapagent.service.ErrorServiceImpl;
import com.airgap.airgapagent.service.SearchEngine;
import com.airgap.airgapagent.service.file.FileCrawlService;
import com.airgap.airgapagent.service.file.FileSearchEngine;
import com.airgap.airgapagent.service.syslog.SyslogService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.env.MockEnvironment;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * com.airgap.airgapagent
 * Created by Jacques Fontignie on 11/13/2021.
 */
class ContinuousRunnerTest {

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
    void run() {
        ContinuousRunner<File> runner = new ContinuousRunner<>();
        AtomicInteger count = new AtomicInteger();
        FileSearchConfiguration configuration = new FileSearchConfiguration();
        Assertions.assertThat(configuration.getLaterThan()).isNull();
        try {
            runner.run(configuration, abstractScanConfiguration -> {
                if (count.incrementAndGet() > 5) {
                    throw new RuntimeException("test");
                }
            });
        } catch (RuntimeException e) {
            //Nothing to do
        }

        Assertions.assertThat(count).hasValue(6);
        Assertions.assertThat(configuration.getLaterThan()).isNotNull();
    }
}
