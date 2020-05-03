package com.airgap.airgapagent.synchro;

import com.airgap.airgapagent.synchro.work.Work;

import java.nio.file.Path;

/**
 * com.airgap.airgapagent.synchro
 * Created by Jacques Fontignie on 4/27/2020.
 */
public class SynchroConfiguration {

    protected Path baseFolder;
    protected Work flow;

    public SynchroConfiguration(Path baseFolder, Work flow) {
        this.baseFolder = baseFolder;
        this.flow = flow;
    }

    public SynchroConfiguration() {
        //Nothing to do
    }

    public String getBaseFolder() {
        return baseFolder.toAbsolutePath().toString();
    }

    public void setBaseFolder(String baseFolder) {
        this.baseFolder = Path.of(baseFolder);
    }

    public Work getFlow() {
        return flow;
    }

    public void setFlow(Work flow) {
        this.flow = flow;
    }

}
