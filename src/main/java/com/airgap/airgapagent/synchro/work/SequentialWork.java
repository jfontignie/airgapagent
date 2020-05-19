package com.airgap.airgapagent.synchro.work;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * com.airgap.airgapagent.synchro
 * Created by Jacques Fontignie on 4/19/2020.
 */
public class SequentialWork<T> extends AbstractWork<T> {

    private List<Work<T>> actions;
    private static final Logger log = LoggerFactory.getLogger(SequentialWork.class);
    private int counter;


    public SequentialWork() {

    }

    @SafeVarargs
    public SequentialWork(Work<T>... works) {
        this(Arrays.asList(works));
    }

    public SequentialWork(List<Work<T>> toPerform) {
        this.actions = toPerform;
    }

    public List<Work<T>> getActions() {
        return actions;
    }

    public void setActions(List<Work<T>> actions) {
        this.actions = actions;
    }

    @Override
    public void init() throws IOException {
        for (Work<T> work : actions) {
            work.init();
        }
    }

    @Override
    public void call(T t) throws IOException {
        counter++;
        for (Work<T> work : actions) {
            work.call(t);
        }
    }

    @Override
    public void close() throws IOException {
        log.info("Number of processed files in task '{}': {}", getName(), counter);
        for (Work<T> work : actions) {
            work.close();
        }
    }
}
