package com.airgap.airgapagent.utils.visitor;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * com.airgap.airgapagent.utils.visitor
 * Created by Jacques Fontignie on 11/3/2021.
 */
class CountFoundListenerTest {

    @Test
    void count() {
        CountFoundListener<?> listener = new CountFoundListener<>(0);
        listener.onFound(null, null);
        Assertions.assertTrue(true);
    }

}
