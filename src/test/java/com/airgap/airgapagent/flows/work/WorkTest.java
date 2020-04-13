package com.airgap.airgapagent.flows.work;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * com.airgap.airgapagent.flows.work
 * Created by Jacques Fontignie on 4/12/2020.
 */
class WorkTest {

    @Test
    public void getName() {
        Work  work = new NoOpWork();
        Assertions.assertNotNull(work.getName());
    }

}
