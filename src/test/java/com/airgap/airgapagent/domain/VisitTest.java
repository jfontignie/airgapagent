package com.airgap.airgapagent.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * com.airgap.airgapagent.domain
 * Created by Jacques Fontignie on 5/7/2020.
 */
class VisitTest {

    @Test
    void testToString() {
        Visit v = new Visit();
        Assertions.assertNotNull(v.toString());
        System.out.println(v);
    }
}
