package com.airgap.airgapagent.synchro;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * com.airgap.airgapagent.synchro
 * Created by Jacques Fontignie on 4/19/2020.
 */
public class SequentialWork implements Work {

    private List<Work> actions;

    public SequentialWork() {

    }

    public SequentialWork(Work... works) {
        this(Arrays.asList(works));
    }

    public SequentialWork(List<Work> toPerform) {
        this.actions = toPerform;
    }

    public List<Work> getActions() {
        return actions;
    }

    public void setActions(List<Work> actions) {
        this.actions = actions;
    }

    @Override
    public void init() throws IOException {
        for (Work work : actions) {
            work.init();
        }
    }

    @Override
    public void call(PathInfo path) throws IOException {
        for (Work work : actions) {
            work.call(path);
        }
    }
}
