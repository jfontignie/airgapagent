package com.airgap.airgapagent.domain;

import java.io.File;
import java.time.Duration;
import java.util.Objects;

public class ExactMatchContextBuilder<T> {
    private T root;
    private File exactMatchFile;
    private File foundFile;
    private File stateFile;
    private Integer minHit;
    private Duration saveInterval;

    public ExactMatchContextBuilder<T> setRoot(T root) {
        this.root = root;
        return this;
    }

    public ExactMatchContextBuilder<T> setExactMatchFile(File exactMatchFile) {
        this.exactMatchFile = exactMatchFile;
        return this;
    }

    public ExactMatchContextBuilder<T> setFoundFile(File foundFile) {
        this.foundFile = foundFile;
        return this;
    }

    public ExactMatchContextBuilder<T> setStateFile(File stateFile) {
        this.stateFile = stateFile;
        return this;
    }

    public ExactMatchContextBuilder<T> setMinHit(int minHit) {
        this.minHit = minHit;
        return this;
    }

    public ExactMatchContextBuilder<T> setSaveInterval(Duration saveInterval) {
        this.saveInterval = saveInterval;
        return this;
    }

    public ExactMatchContext<T> createExactMatchContext() {
        Objects.requireNonNull(root);
        Objects.requireNonNull(exactMatchFile);
        Objects.requireNonNull(foundFile);
        Objects.requireNonNull(stateFile);
        Objects.requireNonNull(minHit);
        Objects.requireNonNull(saveInterval);
        return new ExactMatchContext<>(root, exactMatchFile, foundFile, stateFile, minHit, saveInterval);
    }
}
