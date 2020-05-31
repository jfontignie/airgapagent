package com.airgap.airgapagent.utils;

/**
 * com.airgap.airgapagent.utils
 * Created by Jacques Fontignie on 5/31/2020.
 */
public interface StateConverter<T> {
    String persist(T t);

    T load(String s);
}
