package com.airgap.airgapagent;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * com.airgap.airgapagent
 * Created by Jacques Fontignie on 11/3/2021.
 */
class AirgapagentApplicationTest {

    @Test
    void emptyMain() {

        AirgapagentApplication.main(new String[]{});
        Assertions.assertTrue(true);
    }

    @Test
    void invalidCommand() {

        AirgapagentApplication.main(new String[]{"fsdjkl"});
        Assertions.assertTrue(true);
    }
}
