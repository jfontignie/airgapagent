package com.airgap.airgapagent;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;

/**
 * com.airgap.airgapagent
 * Created by Jacques Fontignie on 11/13/2021.
 */
class SimpleRunnerTest {

    @Test
    void run() {
        SimpleRunner<File> runner = new SimpleRunner<>();
        runner.run(null, (c) -> {
        });

        Assertions.assertThrows(
                IllegalStateException.class,
                () -> runner.run(null, (c) -> {
                    throw new IllegalStateException("invalid");
                })
        );

    }
}
