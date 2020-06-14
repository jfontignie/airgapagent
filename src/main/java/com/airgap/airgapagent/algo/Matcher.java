package com.airgap.airgapagent.algo;

import java.io.IOException;
import java.io.Reader;
import java.util.function.Consumer;

/**
 * com.airgap.airgapagent.algo.ahocorasick
 * Created by Jacques Fontignie on 6/14/2020.
 */
public interface Matcher {
    void match(Reader reader, Consumer<MatchingResult> target) throws IOException;

    void match(String text, Consumer<MatchingResult> consumer) throws IOException;
}
