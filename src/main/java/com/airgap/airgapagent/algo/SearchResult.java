package com.airgap.airgapagent.algo;

import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * com.airgap.airgapagent.algo
 * Created by Jacques Fontignie on 5/29/2020.
 */
public class SearchResult {
    public final int startIndex;

    public final int endIndex;

    public final int matchLength;

    public final char[] pattern;

    public SearchResult(final int endIndex, final char[] pattern) {
        startIndex = endIndex - pattern.length;
        this.endIndex = endIndex;
        matchLength = pattern.length;
        this.pattern = pattern;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", SearchResult.class.getSimpleName() + "[", "]")
                .add("startIndex=" + startIndex)
                .add("endIndex=" + endIndex)
                .add("matchLength=" + matchLength)
                .add("pattern=" + Arrays.toString(pattern))
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SearchResult)) return false;
        SearchResult that = (SearchResult) o;
        return startIndex == that.startIndex &&
                endIndex == that.endIndex &&
                matchLength == that.matchLength &&
                Arrays.equals(pattern, that.pattern);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(startIndex, endIndex, matchLength);
        result = 31 * result + Arrays.hashCode(pattern);
        return result;
    }
}
