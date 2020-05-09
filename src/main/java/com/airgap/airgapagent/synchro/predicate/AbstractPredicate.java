package com.airgap.airgapagent.synchro.predicate;

import java.io.IOException;

/**
 * com.airgap.airgapagent.synchro.predicate
 * Created by Jacques Fontignie on 5/9/2020.
 */
public abstract class AbstractPredicate implements Predicate {
    @Override
    public void init() throws IOException {
        //Nothing to do
    }

    @Override
    public void close() throws IOException {
        //Nothing to do
    }
}
