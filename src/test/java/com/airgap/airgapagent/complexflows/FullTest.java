package com.airgap.airgapagent.complexflows;

import com.airgap.airgapagent.files.FileWatcher;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

/**
 * com.airgap.airgapagent.complexflows
 * Created by Jacques Fontignie on 4/14/2020.
 */
public class FullTest {

    @Test
    public void testFull() {

        FileWatcher.Builder.aNewWatch(Path.of("target")).build();

    }
}
