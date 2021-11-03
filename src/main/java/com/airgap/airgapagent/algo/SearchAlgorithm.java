package com.airgap.airgapagent.algo;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.function.Consumer;

/**
 * com.airgap.airgapagent.algo.ahocorasick
 * Created by Jacques Fontignie on 6/14/2020.
 */
public interface SearchAlgorithm {
    void match(Reader reader, Consumer<SearchResult> target) throws IOException;

    default void match(String text, Consumer<SearchResult> consumer) throws IOException {
        StringReader stringReader = new StringReader(text);
        match(stringReader, consumer);
    }
}
