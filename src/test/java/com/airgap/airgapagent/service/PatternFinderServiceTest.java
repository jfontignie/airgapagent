package com.airgap.airgapagent.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.StringReader;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * com.airgap.airgapagent.service
 * Created by Jacques Fontignie on 5/29/2020.
 */
class PatternFinderServiceTest {
    @Test
    public void testFinder() {
        PatternFinderService service = new PatternFinderService();
        Pattern pattern = Pattern.compile("line.?");
        SuperStringReader reader = new SuperStringReader("one upon a time a lineA in the fsaffsag lineB");

        Set<String> expected = new HashSet<>(Set.of("lineA", "lineB"));
        service.listPattern(reader, pattern).subscribe(s -> {
            Assertions.assertTrue(expected.remove(s));
        });
        Assertions.assertTrue(expected.isEmpty());
        Assertions.assertTrue(reader.isClosed());
    }

    private static class SuperStringReader extends StringReader {
        private boolean closed = false;

        public SuperStringReader(String s) {
            super(s);
        }

        @Override
        public void close() {
            this.closed = true;
            super.close();
        }

        public boolean isClosed() {
            return closed;
        }
    }
}
