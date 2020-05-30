package com.airgap.airgapagent.utils;

import java.time.Duration;

/**
 * com.airgap.airgapagent.service
 * Created by Jacques Fontignie on 5/29/2020.
 */
public class IntervalRunner {

    private final Duration duration;
    private long lastTrigger = 0;

    public IntervalRunner(Duration duration, boolean runFirstCall) {
        if (!runFirstCall) {
            lastTrigger = System.currentTimeMillis();
        }
        this.duration = duration;
    }

    public static IntervalRunner of(Duration duration, boolean runFirstCall) {
        return new IntervalRunner(duration, runFirstCall);
    }

    public void trigger(Runnable runnable) {
        long current = System.currentTimeMillis();
        if (current - lastTrigger > duration.toMillis()) {
            lastTrigger = current;
            runnable.run();
        }
    }
}
