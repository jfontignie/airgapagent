package com.airgap.airgapagent.synchro.work;

import com.airgap.airgapagent.synchro.utils.PathInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * com.airgap.airgapagent.synchro
 * Created by Jacques Fontignie on 4/19/2020.
 */
public class SequentialWork implements CloseableWork {

    private List<Work> actions;
    private static final Logger log = LoggerFactory.getLogger(SequentialWork.class);
    private int counter;


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
        counter++;
        for (Work work : actions) {
            work.call(path);
        }
    }

    @Override
    public void close() throws IOException {
        log.info("Number of processed files: {}", counter);
        for (Work work : actions) {
            if (work instanceof CloseableWork) {
                ((CloseableWork) work).close();
            }
        }
    }
}
