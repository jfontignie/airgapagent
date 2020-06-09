package com.airgap.airgapagent.configuration;

import com.beust.jcommander.Parameter;

import java.io.File;

/**
 * com.airgap.airgapagent.configuration
 * Created by Jacques Fontignie on 6/8/2020.
 */
public abstract class AbstractSearchAction<T> extends AbstractScanAction<T> {
    @Parameter(
            names = "-found",
            description = "File containing the output in a CSV format.",
            required = true)
    private File foundLocation;

    public File getFoundLocation() {
        return foundLocation;
    }

    public void setFoundLocation(File foundLocation) {
        this.foundLocation = foundLocation;
    }

}
