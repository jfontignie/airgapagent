package com.airgap.airgapagent;

import com.airgap.airgapagent.configuration.FileSearchConfiguration;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * com.airgap.airgapagent
 * Created by Jacques Fontignie on 11/13/2021.
 */
class ContinuousRunnerTest {

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
