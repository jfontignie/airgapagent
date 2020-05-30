package com.airgap.airgapagent.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * com.airgap.airgapagent.service
 * Created by Jacques Fontignie on 5/30/2020.
 */
class StateStoreTest {


    @Test
    void save() throws IOException {
        File stateLocation = new File("target/StateStoreTest.dat");
        Files.deleteIfExists(stateLocation.toPath());
        StateStore store = new StateStore(stateLocation);
        FileWalkerContext context = FileWalkerContext.of(ConstantsTest.SAMPLE_FOLDER);
        store.load(context);
        Assertions.assertNull(context.getRefefenceFile());
        File abcd = new File("abcd");
        context.setLastFileToVisit(abcd);
        store.save(context);
        context.setLastFileToVisit(null);
        store.load(context);
        Assertions.assertEquals(abcd, context.getRefefenceFile());
        store.save(context);
        Files.deleteIfExists(stateLocation.toPath());

    }
}
