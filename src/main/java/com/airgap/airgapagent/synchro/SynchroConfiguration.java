package com.airgap.airgapagent.synchro;

import com.airgap.airgapagent.synchro.utils.PathInfo;
import com.airgap.airgapagent.synchro.work.Work;

import java.nio.file.Path;

/**
 * com.airgap.airgapagent.synchro
 * Created by Jacques Fontignie on 4/27/2020.
 */
public class SynchroConfiguration {

    protected Path baseFolder = Path.of(".");
    protected Work<PathInfo> flow = new NoOpWork<>();

    public SynchroConfiguration(Path baseFolder, Work<PathInfo> flow) {
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

    public Work<PathInfo> getFlow() {
        return flow;
    }

    public void setFlow(Work<PathInfo> flow) {
        this.flow = flow;
    }

}
