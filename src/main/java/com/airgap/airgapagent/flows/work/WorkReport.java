package com.airgap.airgapagent.flows.work;

/**
 * com.airgap.airgapagent.flows.work
 * Created by Jacques Fontignie on 4/12/2020.
 */
public interface WorkReport {
    WorkStatus getStatus();

    Throwable getError();

    WorkContext getWorkContext();

}
