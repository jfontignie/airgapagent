package com.airgap.airgapagent.domain;

import java.util.StringJoiner;

/**
 * com.airgap.airgapagent.domain
 * Created by Jacques Fontignie on 5/30/2020.
 */
public class ExactMatchingResult<T> {
    private final T source;
    private final int occurrences;

    public ExactMatchingResult(T source, int occurrences) {
        this.source = source;
        this.occurrences = occurrences;
    }

    public T getSource() {
        return source;
    }

    public int getOccurrences() {
        return occurrences;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ")
                .add("source=" + source)
                .add("occurrences=" + occurrences)
                .toString();
    }
}
