package com.airgap.airgapagent.utils;

import com.airgap.airgapagent.utils.exceptions.ExceptionUtils;
import com.airgap.airgapagent.utils.exceptions.ThrowableStatement;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicBoolean;

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

    @Test
    void run() {
        AtomicBoolean called = new AtomicBoolean(false);
        ExceptionUtils.run((ThrowableStatement<Exception>) () -> {
            //
        }, e -> called.set(true));
        Assertions.assertFalse(called.get());

        ExceptionUtils.run((ThrowableStatement<Exception>) () -> {
            throw new RuntimeException("here");
        }, e -> called.set(true));
        Assertions.assertTrue(called.get());
    }
}
