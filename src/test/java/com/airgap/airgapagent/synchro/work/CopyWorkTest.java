package com.airgap.airgapagent.synchro.work;

import com.airgap.airgapagent.synchro.utils.PathInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * com.airgap.airgapagent.synchro.work
 * Created by Jacques Fontignie on 4/26/2020.
 */
class CopyWorkTest {

    private static final String TARGET_FOLDER = "target/target";
    private CopyWork work;

    @BeforeEach
    void setUp() throws IOException {
        work = new CopyWork();
        work.setTarget(TARGET_FOLDER);
        Assertions.assertEquals(TARGET_FOLDER, work.getTarget().replace("\\", "/"));
        work.init();
    }

    @Test
    void callNoOverride() throws IOException {
        work.setOverride(false);
        work.call(PathInfo.of("src/test/resources", "src/test/resources/sample/sample.txt"));
        Assertions.assertTrue(Files.exists(Path.of("target/target/sample/sample.txt")));
    }

    @Test
    void call() throws IOException {
        work.call(PathInfo.of("src/test/resources", "src/test/resources/sample/sample.txt"));
        Assertions.assertTrue(Files.exists(Path.of("target/target/sample/sample.txt")));
    }

    @Test
    void createNewFolder() throws IOException {
        String targetFolder = "target/" + System.currentTimeMillis();
        work.setTarget(targetFolder);
        Assertions.assertTrue(Files.notExists(Path.of(targetFolder)));
        work.init();
        Assertions.assertTrue(Files.exists(Path.of(targetFolder)));
    }
}
