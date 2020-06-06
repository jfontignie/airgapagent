package com.airgap.airgapagent.domain;

import java.io.File;
import java.time.Duration;

/**
 * com.airgap.airgapagent.domain
 * Created by Jacques Fontignie on 5/30/2020.
 */
public class ExactMatchContext<T> {
    private final T root;
    private final File exactMatchFile;
    private final File foundFile;
    private final File stateFile;
    private final boolean syslog;

    private final int minHit;
    private final Duration saveInterval;


    @SuppressWarnings("java:S107")
    public ExactMatchContext(T root,
                             File exactMatchFile,
                             File foundFile,
                             File stateFile,
                             int minHit,
                             Duration saveInterval,
                             boolean syslog) {
        this.root = root;
        this.exactMatchFile = exactMatchFile;
        this.foundFile = foundFile;
        this.stateFile = stateFile;
        this.minHit = minHit;
        this.saveInterval = saveInterval;
        this.syslog = syslog;
    }

    public T getRoot() {
        return root;
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

    public int getMinHit() {
        return minHit;
    }

    public Duration getSaveInterval() {
        return saveInterval;
    }

    public boolean isSyslog() {
        return syslog;
    }
}
