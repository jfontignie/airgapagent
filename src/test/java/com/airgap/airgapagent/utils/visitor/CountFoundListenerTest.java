package com.airgap.airgapagent.utils.visitor;

import com.airgap.airgapagent.utils.CrawlState;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * com.airgap.airgapagent.utils.visitor
 * Created by Jacques Fontignie on 11/3/2021.
 */
class CountFoundListenerTest {

    @Test
    void count() {
        CountFoundListener<String> listener = new CountFoundListener<>(0);
        listener.onFound(CrawlState.of(""), null);
        Assertions.assertTrue(true);
    }

}
