package com.airgap.airgapagent.configuration;

import java.io.File;
import java.time.Duration;

/**
 * com.airgap.airgapagent.configuration
 * Created by Jacques Fontignie on 6/9/2020.
 */
public interface ScanAction<T> {
    long getSchedule();

    int getMinHit();

    File getCorpusLocation();

    T getRootLocation();

    File getStateLocation();

    boolean isSyslog();

    Duration getSaveInterval();
}
