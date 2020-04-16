package com.airgap.airgapagent.flows.work;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * com.airgap.airgapagent.flows.work
 * Created by Jacques Fontignie on 4/12/2020.
 */
class NoOpWorkTest {

    private final NoOpWork work = new NoOpWork();


    @Test
    public void call() {
        WorkReport workReport = work.call(new WorkContext());
        org.junit.jupiter.api.Assertions.assertNotNull(workReport);
        Assertions.assertEquals(WorkStatus.COMPLETED, workReport.getStatus());
    }

}
