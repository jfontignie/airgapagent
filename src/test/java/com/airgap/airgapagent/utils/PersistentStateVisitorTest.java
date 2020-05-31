package com.airgap.airgapagent.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Duration;

/**
 * com.airgap.airgapagent.service
 * Created by Jacques Fontignie on 5/30/2020.
 */
class PersistentStateVisitorTest {

    @Test
    void walk() throws IOException {
        File stateLocation = new File("target/PersistentFileWalkerTest.dat");

        WalkerContext<File> walkerContext = WalkerContext.of(ConstantsTest.SAMPLE_FOLDER);
        Files.deleteIfExists(stateLocation.toPath());


        PersistentStateVisitor<File> persistentFileWalker = new PersistentStateVisitor<>(
                stateLocation,
                Duration.ofSeconds(1),
                walkerContext,
                FileStateConverter.of());

        persistentFileWalker.init();
        Assertions.assertFalse(stateLocation.exists());

        File a = new File("a");
        walkerContext.setReference(a);
        persistentFileWalker.persist();

        Assertions.assertTrue(stateLocation.exists());
        persistentFileWalker.close();

        Assertions.assertTrue(stateLocation.exists());

        Files.deleteIfExists(stateLocation.toPath());
    }
}
