package com.airgap.airgapagent.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

/**
 * com.airgap.airgapagent.service
 * Created by Jacques Fontignie on 5/30/2020.
 */
public class StateStore<T> {

    private static final Logger log = LoggerFactory.getLogger(StateStore.class);
    public static final String CRAWL_STATE = "crawl_state";

    private final File stateLocation;
    private final Serializer<T> converter;


    public StateStore(File stateLocation, Serializer<T> converter) {
        this.stateLocation = stateLocation;
        this.converter = converter;
    }

    public void load(CrawlState<T> context) {
        String value = StateSaver.get(stateLocation, CRAWL_STATE);
        if (value != null) {
            log.info("Last state detected: {}", value);
            context.setCurrent(converter.load(value));
        }
    }

    public void save(CrawlState<T> context) {
        String state = converter.persist(context.getCurrent());
        save(state);
    }

    private void save(String state) {
        try {
            StateSaver.set(stateLocation, CRAWL_STATE, state);
        } catch (IOException e) {
            log.error("Impossible to save state", e);
        }
    }

    public void clear() {
        save((String) null);
    }

}
