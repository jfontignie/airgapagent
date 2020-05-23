package com.airgap.airgapagent.synchro;

import com.airgap.airgapagent.synchro.work.AbstractWork;

/**
 * com.airgap.airgapagent.synchro
 * Created by Jacques Fontignie on 5/21/2020.
 */
public class NoOpWork<T> extends AbstractWork<T> {
    @Override
    public void call(T t) {
        //Do nothing
    }
}
