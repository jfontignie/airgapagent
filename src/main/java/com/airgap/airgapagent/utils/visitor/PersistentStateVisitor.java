package com.airgap.airgapagent.utils.visitor;

import com.airgap.airgapagent.utils.CrawlState;
import com.airgap.airgapagent.utils.StateConverter;
import com.airgap.airgapagent.utils.StateStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * com.airgap.airgapagent.service
 * Created by Jacques Fontignie on 5/30/2020.
 */
public class PersistentStateVisitor<T> implements StateVisitor {
    private static final Logger log = LoggerFactory.getLogger(PersistentStateVisitor.class);

    private final File stateLocation;
    private final CrawlState<T> crawlState;
    private final StateConverter<T> converter;

    private StateStore<T> stateStore;

    public PersistentStateVisitor(File stateLocation, CrawlState<T> crawlState, StateConverter<T> converter) {
        this.stateLocation = stateLocation;
        this.crawlState = crawlState;
        this.converter = converter;
    }

    public void init() {
        stateStore = new StateStore<>(stateLocation, converter);
        stateStore.load(crawlState);
    }

    @Override
    public void visit(int analysed) {
        log.debug("Saving state");
        stateStore.save(crawlState);

    }

    @Override
    public void close() {
        stateStore.clear();
    }


}
