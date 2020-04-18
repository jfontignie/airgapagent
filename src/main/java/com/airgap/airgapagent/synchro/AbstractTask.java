package com.airgap.airgapagent.synchro;

import java.io.IOException;
import java.util.List;

/**
 * com.airgap.airgapagent.synchro
 * Created by Jacques Fontignie on 4/17/2020.
 */
public abstract class AbstractTask implements Task {

    private final TaskType taskType;
    private String name;
    private List<Task> thenTasks;

    public AbstractTask(TaskType taskType) {
        this.taskType = taskType;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public TaskType getTaskType() {
        return taskType;
    }

    public List<Task> getThen() {
        return thenTasks;
    }

    public void setThen(List<Task> thenTasks) {
        this.thenTasks = thenTasks;
    }

    void callNext(PathInfo path) throws IOException {
        for (Task task : getThen()) {
            task.call(path);
        }
    }
}
