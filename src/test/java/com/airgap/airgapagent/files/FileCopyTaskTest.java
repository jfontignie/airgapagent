package com.airgap.airgapagent.files;

import javafx.util.Pair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

/**
 * com.airgap.airgapagent.files
 * Created by Jacques Fontignie on 4/13/2020.
 */
class FileCopyTaskTest {

    @Test
    void call() {
    }

    @Test
    public void testBuildUniquePath() {
        Path f = FileCopyTask.Target.buildNewUniquePath(Path.of("file.name"));
        Assertions.assertTrue(f.toString().matches("file..*.name"));
    }

    @Test
    public void testSeparateExtension() {
        Assertions.assertEquals(new Pair<>("File",".name"),FileCopyTask.Target.separateExtension("File.name"));
        Assertions.assertEquals(new Pair<>("",".name"),FileCopyTask.Target.separateExtension(".name"));
        Assertions.assertEquals(new Pair<>("File",""),FileCopyTask.Target.separateExtension("File"));

    }
}
