package com.airgap.airgapagent.algo.regex;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.StringReader;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * com.airgap.airgapagent.algo.regex
 * Created by Jacques Fontignie on 6/14/2020.
 */
class RegexMatcherTest {

    @Test
    void match() {
        RegexMatcher matcher = new RegexMatcher(Set.of("pwd", "password"));
        StringReader reader = new StringReader("this contains a password");
        AtomicInteger count = new AtomicInteger();
        matcher.match(reader, matchingResult -> count.incrementAndGet());
        Assertions.assertEquals(1, count.get());

    }
}
