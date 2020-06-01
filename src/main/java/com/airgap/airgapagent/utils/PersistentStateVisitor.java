package com.airgap.airgapagent.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.File;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

/**
 * com.airgap.airgapagent.service
 * Created by Jacques Fontignie on 5/30/2020.
 */
public class PersistentStateVisitor<T> implements Closeable {
    private static final Logger log = LoggerFactory.getLogger(PersistentStateVisitor.class);

    private final File stateLocation;
    private final WalkerContext<T> walkerContext;
    private final StateConverter<T> converter;
    private final Duration saveInterval;

    private IntervalRunner runner;
    private StateStore<T> stateStore;
    private Instant start;

    public PersistentStateVisitor(File stateLocation, Duration saveInterval, WalkerContext<T> walkerContext, StateConverter<T> converter) {
        this.stateLocation = stateLocation;
        this.walkerContext = walkerContext;
        this.converter = converter;
        this.saveInterval = saveInterval;
    }

    public void init() {
        stateStore = new StateStore<>(stateLocation, converter);
        stateStore.load(walkerContext);
        runner = IntervalRunner.of(saveInterval, true);
        start = Instant.now();
    }

    public void persist() {
        Objects.requireNonNull(runner, "Init not called");

        runner.trigger(counter -> {
            int visited = walkerContext.getVisited();
            String progress = "n/a";
            String estimate = "n/a";
            if (visited != 0) {
                progress = String.valueOf(counter * 100 / visited);
                long seconds = ChronoUnit.SECONDS.between(start, Instant.now());

                seconds = (visited - counter) * seconds / counter;
                estimate = String.format(
                        "%dH:%02dM:%02dS",
                        seconds / 3600,
                        (seconds % 3600) / 60,
                        seconds % 60);
            }
            log.info("Running {} / {} ({} %) - estimate: {}",
                    counter,
                    visited,
                    progress,
                    estimate);
            stateStore.save(walkerContext);
        });
    }

    @Override
    public void close() {
        stateStore.clear();
    }


}
