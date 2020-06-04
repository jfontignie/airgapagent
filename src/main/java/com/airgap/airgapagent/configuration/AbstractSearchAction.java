package com.airgap.airgapagent.configuration;

/**
 * com.airgap.airgapagent.configuration
 * Created by Jacques Fontignie on 6/3/2020.
 */
public class AbstractSearchAction implements Action {

    private int minHit;
    private String corpusLocation;
    private String folderLocation;


    public AbstractSearchAction(int minHit, String corpusLocation, String folderLocation) {
        this.minHit = minHit;
        this.corpusLocation = corpusLocation;
        this.folderLocation = folderLocation;
    }

    int getMinHit() {
        return minHit;
    }

    public void setMinHit(int minHit) {
        this.minHit = minHit;
    }

    String getCorpusLocation() {
        return corpusLocation;
    }

    public void setCorpusLocation(String corpusLocation) {
        this.corpusLocation = corpusLocation;
    }

    String getFolderLocation() {
        return folderLocation;
    }

    public void setFolderLocation(String folderLocation) {
        this.folderLocation = folderLocation;
    }

    @Override
    public ActionType getType() {
        return ActionType.SEARCH;
    }
}
