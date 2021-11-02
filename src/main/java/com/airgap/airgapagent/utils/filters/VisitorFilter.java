package com.airgap.airgapagent.utils.filters;

/**
 * com.airgap.airgapagent.service
 * Created by Jacques Fontignie on 5/23/2020.
 */
public interface VisitorFilter<T> {

    boolean accept(T t);
}
