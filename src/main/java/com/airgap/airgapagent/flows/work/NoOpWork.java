package com.airgap.airgapagent.flows.work;

/**
 * com.airgap.airgapagent.flows.work
 * Created by Jacques Fontignie on 4/12/2020.
 */
public class NoOpWork implements Work{

    @Override
    public WorkReport call(WorkContext workContext) {
        return new DefaultWorkReport(WorkStatus.COMPLETED, workContext);
    }
}
