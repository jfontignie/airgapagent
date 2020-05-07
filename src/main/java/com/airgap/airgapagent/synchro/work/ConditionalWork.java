package com.airgap.airgapagent.synchro.work;

import com.airgap.airgapagent.synchro.predicate.Predicate;
import com.airgap.airgapagent.synchro.utils.PathInfo;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.IOException;

/**
 * com.airgap.airgapagent.synchro
 * Created by Jacques Fontignie on 4/19/2020.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConditionalWork extends AbstractWork {

    private Predicate predicate;
    private Work nextIfSucceeded;
    private Work nextIfFailed;

    public ConditionalWork(Predicate predicate, Work nextIfSucceeded, Work nextIfFailed) {
        this.predicate = predicate;
        this.nextIfSucceeded = nextIfSucceeded;
        this.nextIfFailed = nextIfFailed;
    }

    public ConditionalWork() {
        //Nothing to do
    }

    public Predicate getPredicate() {
        return predicate;
    }

    public void setPredicate(Predicate predicate) {
        this.predicate = predicate;
    }

    public Work getNextIfSucceeded() {
        return nextIfSucceeded;
    }

    public void setNextIfSucceeded(Work nextIfSucceeded) {
        this.nextIfSucceeded = nextIfSucceeded;
    }

    public Work getNextIfFailed() {
        return nextIfFailed;
    }

    public void setNextIfFailed(Work nextIfFailed) {
        this.nextIfFailed = nextIfFailed;
    }

    @Override
    public void init() throws IOException {
        predicate.init();
        if (nextIfSucceeded != null) {
            nextIfSucceeded.init();
        }
        if (nextIfFailed != null) {
            nextIfFailed.init();
        }
    }

    @Override
    public void call(PathInfo path) throws IOException {
        boolean result = predicate.call(path);
        Work next = result ? nextIfSucceeded : nextIfFailed;
        if (next != null) {
            next.call(path);
        }
    }

    @Override
    public void close() throws IOException {
        close(nextIfFailed);
        close(nextIfSucceeded);
        predicate.close();
    }

    private void close(Work action) throws IOException {
        action.close();
    }
}
