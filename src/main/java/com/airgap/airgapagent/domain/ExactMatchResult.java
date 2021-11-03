package com.airgap.airgapagent.domain;

import com.airgap.airgapagent.utils.DataReader;

import java.util.StringJoiner;

/**
 * com.airgap.airgapagent.domain
 * Created by Jacques Fontignie on 5/30/2020.
 */
public class ExactMatchResult<T> {
    private final DataReader<T> source;
    private final int occurrences;

    public ExactMatchResult(DataReader<T> source, int occurrences) {
        this.source = source;
        this.occurrences = occurrences;
    }

    public DataReader<T> getDataSource() {
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
