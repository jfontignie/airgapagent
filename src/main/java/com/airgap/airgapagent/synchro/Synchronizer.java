package com.airgap.airgapagent.synchro;

import java.io.Serializable;
import java.util.List;

/**
 * com.airgap.airgapagent.synchro
 * Created by Jacques Fontignie on 4/17/2020.
 */
public class Synchronizer implements Serializable {

    private String baseFolder;
    private int earlierThan;
    private List<Task> tasks;

    public Synchronizer() {
        //Nothing to do
    }

    public String getBaseFolder() {
        return baseFolder;
    }

    public void setBaseFolder(String baseFolder) {
        this.baseFolder = baseFolder;
    }

    public int getEarlierThan() {
        return earlierThan;
    }

    public void setEarlierThan(int earlierThan) {
        this.earlierThan = earlierThan;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
