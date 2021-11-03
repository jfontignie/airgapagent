package com.airgap.airgapagent.utils;

import com.airgap.airgapagent.utils.file.FileSerializer;
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
        FileSerializer converter = FileSerializer.of();
        StateStore<File> store = new StateStore<>(stateLocation, converter);
        CrawlState<File> context = CrawlState.of(ConstantsTest.SAMPLE_FOLDER);

        store.load(context);
        Assertions.assertNull(context.getCurrent());
        File abcd = new File("abcd");
        context.setCurrent(abcd);
        store.save(context);
        context.reset();
        store.load(context);
        Assertions.assertEquals(abcd, context.getCurrent());
        store.save(context);
        Files.deleteIfExists(stateLocation.toPath());

    }
}
