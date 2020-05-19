package com.airgap.airgapagent.synchro.predicate;

/**
 * com.airgap.airgapagent.synchro.predicate
 * Created by Jacques Fontignie on 5/9/2020.
 */
public abstract class AbstractPredicate<T> implements Predicate<T> {
    @Override
    public void init() {
        //Nothing to do
    }

    @Override
    public void close() {
        //Nothing to do
    }
}
