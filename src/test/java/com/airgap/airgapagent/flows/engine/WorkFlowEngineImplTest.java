package com.airgap.airgapagent.flows.engine;

import com.airgap.airgapagent.flows.work.WorkContext;
import com.airgap.airgapagent.flows.workflow.SequentialFlow;
import org.junit.jupiter.api.Test;

/**
 * com.airgap.airgapagent.flows.engine
 * Created by Jacques Fontignie on 4/12/2020.
 */
class WorkFlowEngineImplTest {
    private final WorkFlowEngine workFlowEngine = new WorkFlowEngineImpl();

    @Test
    void run() {
        workFlowEngine.run(SequentialFlow.Builder.aNewSequentialFlow().build(),new WorkContext());
    }
}
