package com.airgap.airgapagent.configuration;

import com.beust.jcommander.Parameter;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

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

    @Parameter(
            names = "-excludeMeta",
            description = "When finding data, you can decide to exclude some metadata key in order to keep the list small")
    private Set<String> exclude = new HashSet<>();

    public File getFoundLocation() {
        return foundLocation;
    }

    public void setFoundLocation(File foundLocation) {
        this.foundLocation = foundLocation;
    }

    public Set<String> getExclude() {
        return exclude;
    }

    public void setExclude(Set<String> exclude) {
        this.exclude = exclude;
    }
}
