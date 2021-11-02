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
class ProgressLogStateVisitorTest {
    @Test
    void walk() {

        CrawlState<File> crawlState = CrawlState.of(ConstantsTest.SAMPLE_FOLDER);


        ProgressLogStateVisitor<File> visitor = new ProgressLogStateVisitor<>(crawlState);

        visitor.init();
        visitor.visit(1);
        visitor.visit(2);

        visitor.close();

        Assertions.assertTrue(true);

    }
}
