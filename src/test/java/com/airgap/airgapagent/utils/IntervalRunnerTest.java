package com.airgap.airgapagent.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * com.airgap.airgapagent.service
 * Created by Jacques Fontignie on 5/29/2020.
 */
class IntervalRunnerTest {

    @SuppressWarnings("java:S2925")
    @Test
    void trigger() throws InterruptedException {
        IntervalRunner trigger = IntervalRunner.of(Duration.ofSeconds(1), true);
        AtomicBoolean called = new AtomicBoolean(false);
        trigger.trigger(() -> called.set(true));
        Assertions.assertTrue(called.get());

        called.set(false);
        trigger.trigger(() -> called.set(true));
        Assertions.assertFalse(called.get());
        Thread.sleep(1010);

        called.set(false);
        trigger.trigger(() -> called.set(true));
        Assertions.assertTrue(called.get());

    }
}
