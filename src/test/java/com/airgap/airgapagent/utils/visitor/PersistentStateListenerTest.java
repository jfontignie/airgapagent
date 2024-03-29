package com.airgap.airgapagent.utils.visitor;

import com.airgap.airgapagent.utils.ConstantsTest;
import com.airgap.airgapagent.utils.CrawlState;
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
class PersistentStateListenerTest {

    @Test
    void walk() throws IOException {
        File stateLocation = new File("target/PersistentFileWalkerTest.dat");

        CrawlState<File> crawlState = CrawlState.of(ConstantsTest.SAMPLE_FOLDER);
        Files.deleteIfExists(stateLocation.toPath());


        PersistentStateListener<File> persistentFileWalker = new PersistentStateListener<>(
                1,
                stateLocation,
                FileSerializer.of());

        persistentFileWalker.onInit(crawlState);
        Assertions.assertFalse(stateLocation.exists());

        File a = new File("a");
        crawlState.setCurrent(a);
        persistentFileWalker.onVisited(crawlState, null);

        crawlState.setCurrent(new File("b"));
        crawlState = CrawlState.of(ConstantsTest.SAMPLE_FOLDER);
        persistentFileWalker.onInit(crawlState);

        Assertions.assertEquals(a, crawlState.getCurrent());

        Assertions.assertTrue(stateLocation.exists());
        persistentFileWalker.onVisited(crawlState, null);

        Assertions.assertTrue(stateLocation.exists());

        Files.deleteIfExists(stateLocation.toPath());
    }


}
