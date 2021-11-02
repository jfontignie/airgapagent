package com.airgap.airgapagent.utils.filters;

/**
 * com.airgap.airgapagent.utils.filters
 * Created by Jacques Fontignie on 11/2/2021.
 */
public class AlwaysVisitorFilter<T> implements VisitorFilter<T> {
    @Override
    public boolean accept(T t) {
        return true;
    }
}
