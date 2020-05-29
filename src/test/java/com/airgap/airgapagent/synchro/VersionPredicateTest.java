package com.airgap.airgapagent.synchro;

import com.airgap.airgapagent.synchro.predicate.VersionPredicate;
import com.airgap.airgapagent.synchro.utils.PathInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;

/**
 * com.airgap.airgapagent.synchro
 * Created by Jacques Fontignie on 4/26/2020.
 */
class VersionPredicateTest {

    @Test
    void test() throws IOException {
        VersionPredicate predicate = new VersionPredicate();
        predicate.setStatusFile("target/state.txt");
        predicate.init();
        Path target = Path.of("target/sample.txt");
        if (Files.notExists(target)) {
            Files.copy(Path.of("src/test/resources/sample/sample.txt"), target);
        }
        PathInfo of = PathInfo.of("target", "target/sample.txt");
        Assertions.assertTrue(predicate.call(of));
        Assertions.assertFalse(predicate.call(of));
        Files.setLastModifiedTime(target, FileTime.fromMillis(System.currentTimeMillis()));
        Assertions.assertTrue(predicate.call(of));


    }

}
