package com.airgap.airgapagent.configuration;

/**
 * com.airgap.airgapagent
 * Created by Jacques Fontignie on 6/3/2020.
 */
public class OutputConfiguration {

    private final String errorFile;

    private final String stateFile;

    public OutputConfiguration(String errorFile, String stateFile) {
        this.errorFile = errorFile;
        this.stateFile = stateFile;
    }

    public String getErrorFile() {
        return errorFile;
    }

    public String getStateFile() {
        return stateFile;
    }
}
