package com.airgap.airgapagent.synchro.predicate;

import com.airgap.airgapagent.synchro.utils.PathInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

/**
 * com.airgap.airgapagent.synchro.predicate
 * Created by Jacques Fontignie on 5/10/2020.
 */
class FileTimePredicateTest {

    @Test
    void call() throws IOException {
        FileTimePredicate predicate = new FileTimePredicate();
        predicate.setOlderThan("600w");
        predicate.init();
        Assertions.assertFalse(predicate.call(PathInfo.of("src/test/resources")));
    }
}
