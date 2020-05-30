package com.airgap.airgapagent.utils;

import com.airgap.airgapagent.service.FileWalkerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Duration;

/**
 * com.airgap.airgapagent.service
 * Created by Jacques Fontignie on 5/30/2020.
 */
class PersistentFileWalkerTest {

    @Test
    public void walk() throws IOException {
        File stateLocation = new File("target/PersistentFileWalkerTest.dat");

        Files.deleteIfExists(stateLocation.toPath());
        PersistentFileWalker persistentFileWalker = new PersistentFileWalker(
                new FileWalkerService(),
                stateLocation,
                Duration.ofSeconds(1));

        Flux<File> flux = persistentFileWalker.listFiles(FileWalkerContext.of(ConstantsTest.SAMPLE_FOLDER));
        Assertions.assertNotNull(flux.blockLast());
        Assertions.assertTrue(stateLocation.exists());

        Files.deleteIfExists(stateLocation.toPath());
    }
}
