package com.airgap.airgapagent.flows.engine;

import com.airgap.airgapagent.flows.work.WorkContext;
import com.airgap.airgapagent.flows.work.WorkReport;
import com.airgap.airgapagent.flows.workflow.WorkFlow;
import org.springframework.lang.NonNull;

/**
 * com.airgap.airgapagent.flows.engine
 * Created by Jacques Fontignie on 4/12/2020.
 */
public interface WorkFlowEngine {

    WorkReport run(@NonNull  WorkFlow workFlow, @NonNull WorkContext workContext);
}
