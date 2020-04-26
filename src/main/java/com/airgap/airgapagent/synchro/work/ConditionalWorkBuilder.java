package com.airgap.airgapagent.synchro.work;

import com.airgap.airgapagent.synchro.predicate.Predicate;

public class ConditionalWorkBuilder {
    private Predicate predicate;
    private Work nextIfSucceeded;
    private Work nextIfFailed;

    public ConditionalWorkBuilder setPredicate(Predicate predicate) {
        this.predicate = predicate;
        return this;
    }

    public ConditionalWorkBuilder setNextIfSucceeded(Work nextIfSucceeded) {
        this.nextIfSucceeded = nextIfSucceeded;
        return this;
    }

    public ConditionalWorkBuilder setNextIfFailed(Work nextIfFailed) {
        this.nextIfFailed = nextIfFailed;
        return this;
    }

    public ConditionalWork createConditionalWork() {
        return new ConditionalWork(predicate, nextIfSucceeded, nextIfFailed);
    }
}
