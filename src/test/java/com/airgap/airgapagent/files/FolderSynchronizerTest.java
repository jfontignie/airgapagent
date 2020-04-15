package com.airgap.airgapagent.files;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * com.airgap.airgapagent.files
 * Created by Jacques Fontignie on 4/15/2020.
 */
class FolderSynchronizerTest {

    @Test
    void synchronize() throws IOException {
        Path first = Paths.get("target");
        Path second = Paths.get("sandbox");
        FolderSynchronizer synchronizer = new FolderSynchronizer(first, second);
        synchronizer.synchronize();

    }

    @Test
    void testPath() {
        Path first = Paths.get("abcd");
        Path second = Paths.get("zxyz");
        Assertions.assertTrue(first.compareTo(second) < 0);
    }
}
