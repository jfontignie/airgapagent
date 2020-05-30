package com.airgap.airgapagent.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * com.airgap.airgapagent.utils
 * Created by Jacques Fontignie on 5/30/2020.
 */
class ExceptionUtilsTest {

    @Test
    void testException() {
        Throwable t = new IllegalStateException("abcd", new RuntimeException("abcd"));

        String expand = ExceptionUtils.expand(t);
        System.out.println(expand);
        Assertions.assertEquals("java.lang.IllegalStateException: abcd->java.lang.RuntimeException: abcd", expand);
    }
}
