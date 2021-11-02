package com.airgap.airgapagent.utils.exceptions;

/**
 * com.airgap.airgapagent.utils.exceptions
 * Created by Jacques Fontignie on 11/2/2021.
 */
public interface ThrowableStatement<T extends Exception> {
    void run() throws T;
}
