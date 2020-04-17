package com.airgap.airgapagent.files;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;

/**
 * com.airgap.airgapagent.files
 * Created by Jacques Fontignie on 4/16/2020.
 */
class FolderSynchronizerTest {

    @Test
    void deleteDifference() throws IOException {
        FolderSynchronizer synchronizer = new FolderSynchronizer(Path.of("src/test/resources"));
        //synchronizer.deleteDifference(new FileMetadata("",""));
    }

    @Test
    void copyDifference() {
    }
}
