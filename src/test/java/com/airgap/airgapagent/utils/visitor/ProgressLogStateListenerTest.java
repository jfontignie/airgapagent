package com.airgap.airgapagent.utils.visitor;

import com.airgap.airgapagent.utils.ConstantsTest;
import com.airgap.airgapagent.utils.CrawlState;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;

/**
 * com.airgap.airgapagent.utils.visitor
 * Created by Jacques Fontignie on 11/2/2021.
 */
class ProgressLogStateListenerTest {
    @Test
    void walk() {

        CrawlState<File> crawlState = CrawlState.of(ConstantsTest.SAMPLE_FOLDER);


        ProgressLogStateListener<File> visitor = new ProgressLogStateListener<>(5);

        visitor.onInit();
        visitor.onFound(1, crawlState, null);
        visitor.onFound(2, crawlState, null);

        visitor.onClose();

        Assertions.assertTrue(true);

    }
}
