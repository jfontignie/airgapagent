package com.airgap.airgapagent.flows.engine;

import com.airgap.airgapagent.flows.work.WorkContext;
import com.airgap.airgapagent.flows.work.WorkReport;
import com.airgap.airgapagent.flows.workflow.WorkFlow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * com.airgap.airgapagent.flows.engine
 * Created by Jacques Fontignie on 4/12/2020.
 */
public class WorkFlowEngineImpl implements WorkFlowEngine {
    private static final Logger logger = LoggerFactory.getLogger(WorkFlowEngineImpl.class);

    @Override
    public WorkReport run(WorkFlow workFlow, WorkContext workContext) {
        logger.info("Running workflow ''{}''", workFlow.getName());
        return workFlow.call(workContext);
    }
}
