package com.airgap.airgapagent.domain;

import java.io.File;
import java.time.Duration;
import java.util.Objects;

public class ExactMatchContextBuilder {
    private File scanFolder;
    private File exactMatchFile;
    private File foundFile;
    private File stateFile;
    private Integer minHit;
    private Integer maxHit;
    private Duration saveInterval;

    public ExactMatchContextBuilder setScanFolder(File scanFolder) {
        this.scanFolder = scanFolder;
        return this;
    }

    public ExactMatchContextBuilder setExactMatchFile(File exactMatchFile) {
        this.exactMatchFile = exactMatchFile;
        return this;
    }

    public ExactMatchContextBuilder setFoundFile(File foundFile) {
        this.foundFile = foundFile;
        return this;
    }

    public ExactMatchContextBuilder setStateFile(File stateFile) {
        this.stateFile = stateFile;
        return this;
    }

    public ExactMatchContextBuilder setMinHit(int minHit) {
        this.minHit = minHit;
        return this;
    }

    public ExactMatchContextBuilder setMaxHit(int maxHit) {
        this.maxHit = maxHit;
        return this;
    }

    public ExactMatchContextBuilder setSaveInterval(Duration saveInterval) {
        this.saveInterval = saveInterval;
        return this;
    }

    public ExactMatchContext createExactMatchContext() {
        Objects.requireNonNull(scanFolder);
        Objects.requireNonNull(exactMatchFile);
        Objects.requireNonNull(foundFile);
        Objects.requireNonNull(stateFile);
        Objects.requireNonNull(minHit);
        Objects.requireNonNull(maxHit);
        Objects.requireNonNull(saveInterval);
        return new ExactMatchContext(scanFolder, exactMatchFile, foundFile, stateFile, minHit, maxHit, saveInterval);
    }
}
