package com.airgap.airgapagent.domain;

import java.io.File;
import java.util.StringJoiner;

/**
 * com.airgap.airgapagent.domain
 * Created by Jacques Fontignie on 5/30/2020.
 */
public class ExactMatchingResult {
    private final File file;
    private final int occurrences;

    public ExactMatchingResult(File file, int occurrences) {
        this.file = file;
        this.occurrences = occurrences;
    }

    public File getFile() {
        return file;
    }

    public int getOccurrences() {
        return occurrences;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ")
                .add("file=" + file)
                .add("occurrences=" + occurrences)
                .toString();
    }
}
