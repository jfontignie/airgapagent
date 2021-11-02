package com.airgap.airgapagent.utils.filters;

import java.io.File;
import java.time.Instant;

/**
 * com.airgap.airgapagent.utils.filters
 * Created by Jacques Fontignie on 11/2/2021.
 */
public class YoungerThanFileFilter implements WalkerFilter<File> {

    private final long threshold;

    public YoungerThanFileFilter(Instant instant) {
        this.threshold = instant.toEpochMilli();
    }

    @Override
    public boolean accept(File file) {
        return file.lastModified() >= threshold;
    }
}
