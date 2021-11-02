package com.airgap.airgapagent.utils.visitor;

import com.airgap.airgapagent.utils.ConstantsTest;
import com.airgap.airgapagent.utils.CrawlState;
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
class PersistentStateVisitorTest {

    @Test
    void walk() throws IOException {
        File stateLocation = new File("target/PersistentFileWalkerTest.dat");

        CrawlState<File> crawlState = CrawlState.of(ConstantsTest.SAMPLE_FOLDER);
        Files.deleteIfExists(stateLocation.toPath());


        PersistentStateVisitor<File> persistentFileWalker = new PersistentStateVisitor<>(
                stateLocation,
                crawlState,
                FileStateConverter.of());

        persistentFileWalker.init();
        Assertions.assertFalse(stateLocation.exists());

        File a = new File("a");
        crawlState.setReference(a);
        persistentFileWalker.visit(1);

        Assertions.assertTrue(stateLocation.exists());
        persistentFileWalker.close();

        Assertions.assertTrue(stateLocation.exists());

        Files.deleteIfExists(stateLocation.toPath());
    }
}
