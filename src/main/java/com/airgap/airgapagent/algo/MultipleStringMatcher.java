package com.airgap.airgapagent.algo;

import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

/**
 * com.airgap.airgapagent.algo
 * Created by Jacques Fontignie on 5/29/2020.
 * <p>
 * https://codereview.stackexchange.com/questions/115624/aho-corasick-for-multiple-exact-string-matching-in-java
 */
public abstract class MultipleStringMatcher {

    public abstract void match(Reader reader, final Consumer<MatchingResult> target, char[]... patterns) throws IOException;


    protected String[] filterPatterns(String[] patterns) {
        Set<String> filter = new HashSet<>(Arrays.asList(patterns));
        return filter.toArray(new String[0]);
    }

    protected char[][] uniquePatterns(final char[][] patterns) {
        final Set<char[]> filter = new HashSet<>(Arrays.asList(patterns));
        return filter.toArray(new char[filter.size()][0]);
    }
}

