package com.airgap.airgapagent.utils;

import com.airgap.airgapagent.utils.file.FileStateConverter;
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
        FileStateConverter converter = FileStateConverter.of();
        StateStore<File> store = new StateStore<>(stateLocation, converter);
        CrawlState<File> context = CrawlState.of(ConstantsTest.SAMPLE_FOLDER);

        store.load(context);
        Assertions.assertNull(context.getReference());
        File abcd = new File("abcd");
        context.setReference(abcd);
        store.save(context);
        context.reset();
        store.load(context);
        Assertions.assertEquals(abcd, context.getReference());
        store.save(context);
        Files.deleteIfExists(stateLocation.toPath());

    }
}
