package com.airgap.airgapagent.algo;

import java.io.IOException;
import java.io.Reader;
import java.util.function.Consumer;

/**
 * com.airgap.airgapagent.algo
 * Created by Jacques Fontignie on 5/29/2020.
 * <p>
 * https://codereview.stackexchange.com/questions/115624/aho-corasick-for-multiple-exact-string-matching-in-java
 */
public interface MultipleStringMatcher {

    void match(Reader reader, final Consumer<MatchingResult> target, char[]... patterns) throws IOException;

}

