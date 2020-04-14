package com.airgap.airgapagent.flows.trigger;

import com.airgap.airgapagent.flows.work.WorkReport;

import java.io.IOException;
import java.util.UUID;

/**
 * com.airgap.airgapagent.flows.trigger
 * Created by Jacques Fontignie on 4/14/2020.
 */
public interface Trigger {

    default String getName() {
        return UUID.randomUUID().toString();
    }


    WorkReport take() throws InterruptedException;

    boolean poll();

    void init() throws IOException;
}
