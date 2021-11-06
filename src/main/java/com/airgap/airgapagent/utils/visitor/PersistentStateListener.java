package com.airgap.airgapagent.utils.visitor;

import com.airgap.airgapagent.utils.CrawlState;
import com.airgap.airgapagent.utils.IntervalRunner;
import com.airgap.airgapagent.utils.Serializer;
import com.airgap.airgapagent.utils.StateStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.time.Duration;

/**
 * com.airgap.airgapagent.service
 * Created by Jacques Fontignie on 5/30/2020.
 */
public class PersistentStateListener<T> extends SearchEventAdapter<T> {
    private static final Logger log = LoggerFactory.getLogger(PersistentStateListener.class);

    private final File stateLocation;
    private final Serializer<T> converter;
    private final IntervalRunner runner;

    private StateStore<T> stateStore;

    public PersistentStateListener(long interval, File stateLocation, Serializer<T> converter) {
        runner = IntervalRunner.of(Duration.ofSeconds(interval), true);
        this.stateLocation = stateLocation;
        this.converter = converter;
    }

    @Override
    public void onInit(CrawlState<T> crawlState) {
        stateStore = new StateStore<>(stateLocation, converter);
        stateStore.load(crawlState);
    }

    @Override
    public void onVisited(CrawlState<T> crawlState, T object) {
        runner.trigger(c -> {
            log.trace("Saving state");
            stateStore.save(crawlState);
        });
    }

    @Override
    public void onClose(CrawlState<T> crawlState) {
        stateStore.clear();
    }


}
