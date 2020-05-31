package com.airgap.airgapagent.service;

/**
 * com.airgap.airgapagent.service
 * Created by Jacques Fontignie on 5/31/2020.
 */
public interface ErrorService {

    void error(Object source, String message, Throwable e);

}
