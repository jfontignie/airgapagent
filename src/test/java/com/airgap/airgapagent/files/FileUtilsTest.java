package com.airgap.airgapagent.files;

import com.airgap.airgapagent.utils.Pair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

/**
 * com.airgap.airgapagent.files
 * Created by Jacques Fontignie on 4/13/2020.
 */
class FileUtilsTest {
    @Test
    public void testBuildUniquePath() {
        Path f = FileUtils.buildNewUniquePath(Path.of("file.name"));
        Assertions.assertTrue(f.toString().matches("file..*.name"));
    }

    @Test
    public void testSeparateExtension() {
        Assertions.assertEquals(new Pair<>("File",".name"),FileUtils.separateExtension("File.name"));
        Assertions.assertEquals(new Pair<>("",".name"),FileUtils.separateExtension(".name"));
        Assertions.assertEquals(new Pair<>("File",""),FileUtils.separateExtension("File"));

    }
}
