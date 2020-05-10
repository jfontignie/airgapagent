package com.airgap.airgapagent.synchro.predicate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Duration;

/**
 * com.airgap.airgapagent.synchro.predicate
 * Created by Jacques Fontignie on 5/10/2020.
 */
class DurationHelperTest {

    @Test
    void simpleParse() {
        Assertions.assertEquals(Duration.ofHours(5), DurationHelper.simpleParse("5h"));
        Assertions.assertEquals(Duration.ofDays(5), DurationHelper.simpleParse("5d"));
        Assertions.assertEquals(Duration.ofDays(5 * 7), DurationHelper.simpleParse("5w"));
        Assertions.assertNull(DurationHelper.simpleParse("5g"));
        Assertions.assertNull(DurationHelper.simpleParse(null));
        Assertions.assertNull(DurationHelper.simpleParse("g"));
        Assertions.assertNull(DurationHelper.simpleParse("1dsadsah"));
    }

}
