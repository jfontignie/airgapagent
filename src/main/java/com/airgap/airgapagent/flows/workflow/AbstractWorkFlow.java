package com.airgap.airgapagent.flows.workflow;

/**
 * com.airgap.airgapagent.flows.workflow
 * Created by Jacques Fontignie on 4/12/2020.
 */
public abstract class AbstractWorkFlow implements WorkFlow{
    private final String name;

    AbstractWorkFlow(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
