package com.airgap.airgapagent.configuration;

/**
 * com.airgap.airgapagent.configuration
 * Created by Jacques Fontignie on 6/4/2020.
 */
public class FileSearchAction extends AbstractSearchAction {
    private final String foundLocation;

    public FileSearchAction(int minHit, String corpusLocation, String folderLocation, String foundLocation) {
        super(minHit, corpusLocation, folderLocation);
        this.foundLocation = foundLocation;
    }

    public String getFoundLocation() {
        return foundLocation;
    }
}
