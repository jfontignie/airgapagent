package com.airgap.airgapagent.utils.visitor;

import com.airgap.airgapagent.utils.ConstantsTest;
import com.airgap.airgapagent.utils.WalkerContext;
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

        WalkerContext<File> walkerContext = WalkerContext.of(ConstantsTest.SAMPLE_FOLDER);


        ProgressLogStateVisitor<File> visitor = new ProgressLogStateVisitor<>(walkerContext);

        visitor.init();
        visitor.visit(1);
        visitor.visit(2);

        visitor.close();

        Assertions.assertTrue(true);

    }
}
