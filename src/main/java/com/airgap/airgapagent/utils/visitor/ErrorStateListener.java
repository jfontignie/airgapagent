package com.airgap.airgapagent.utils.visitor;

import com.airgap.airgapagent.service.ErrorService;
import com.airgap.airgapagent.utils.DataReader;

/**
 * com.airgap.airgapagent.utils.visitor
 * Created by Jacques Fontignie on 11/3/2021.
 */
public class ErrorStateListener<T> extends SearchEventAdapter<T> {

    private final ErrorService errorService;

    public ErrorStateListener(ErrorService errorService) {
        this.errorService = errorService;
    }

    @Override
    public void onError(DataReader<T> object, Throwable error) {
        errorService.error(object.getSource(), "Error while reading file", error);
    }
}
