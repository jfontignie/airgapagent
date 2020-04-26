package com.airgap.airgapagent.synchro;

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
class RecentPredicateTest {

    @Test
    public void test() throws IOException {
        RecentPredicate predicate = new RecentPredicate();
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
