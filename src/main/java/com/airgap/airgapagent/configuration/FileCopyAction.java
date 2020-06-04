package com.airgap.airgapagent.configuration;

import java.util.Set;

/**
 * com.airgap.airgapagent.configuration
 * Created by Jacques Fontignie on 6/3/2020.
 */
public class FileCopyAction extends AbstractSearchAction {

    private final String targetFolder;
    private final Set<CopyOption> copyOptions;

    public FileCopyAction(int minHit, String corpusLocation,
                          String folderLocation,
                          String targetFolder,
                          Set<CopyOption> copyOptions) {
        super(minHit, corpusLocation, folderLocation);
        this.targetFolder = targetFolder;
        this.copyOptions = copyOptions;
    }


    @Override
    public ActionType getType() {
        return ActionType.COPY;
    }

    String getTargetFolder() {
        return targetFolder;
    }

    Set<CopyOption> getCopyOptions() {
        return copyOptions;
    }


}
