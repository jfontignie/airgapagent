package com.airgap.airgapagent.domain;

import java.io.File;
import java.time.Duration;

/**
 * com.airgap.airgapagent.domain
 * Created by Jacques Fontignie on 5/30/2020.
 */
public class ExactMatchContext {
    private final File scanFolder;
    private final File exactMatchFile;
    private final File foundFile;
    private final File stateFile;
    private final File errorFile;

    private final int minHit;
    private final int maxHit;
    private final Duration saveInterval;


    @SuppressWarnings("java:S107")
    public ExactMatchContext(File scanFolder,
                             File exactMatchFile,
                             File foundFile,
                             File stateFile,
                             File errorFile,
                             int minHit,
                             int maxHit,
                             Duration saveInterval) {
        this.scanFolder = scanFolder;
        this.exactMatchFile = exactMatchFile;
        this.foundFile = foundFile;
        this.stateFile = stateFile;
        this.errorFile = errorFile;
        this.minHit = minHit;
        this.maxHit = maxHit;
        this.saveInterval = saveInterval;
    }

    public File getScanFolder() {
        return scanFolder;
    }

    public File getExactMatchFile() {
        return exactMatchFile;
    }

    public File getFoundFile() {
        return foundFile;
    }

    public File getStateFile() {
        return stateFile;
    }

    public int getMaxHit() {
        return maxHit;
    }

    public int getMinHit() {
        return minHit;
    }

    public Duration getSaveInterval() {
        return saveInterval;
    }

    public File getErrorFile() {
        return errorFile;
    }
}
