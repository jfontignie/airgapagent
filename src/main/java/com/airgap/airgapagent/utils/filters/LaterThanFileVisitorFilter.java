package com.airgap.airgapagent.utils.filters;

import java.io.File;
import java.time.Instant;
import java.util.Date;

/**
 * com.airgap.airgapagent.utils.filters
 * Created by Jacques Fontignie on 11/2/2021.
 */
public class LaterThanFileVisitorFilter implements VisitorFilter<File> {

    private final long threshold;

    public LaterThanFileVisitorFilter(Date date) {
        this(date.toInstant());
    }

    public LaterThanFileVisitorFilter(Instant instant) {
        this.threshold = instant.toEpochMilli();
    }

    @Override
    public boolean accept(File file) {
        return !file.isFile() || file.lastModified() >= threshold;
    }
}
