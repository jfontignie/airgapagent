package com.airgap.airgapagent.synchro.work;

import com.airgap.airgapagent.synchro.predicate.Predicate;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * com.airgap.airgapagent.synchro
 * Created by Jacques Fontignie on 4/19/2020.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConditionalWork<T> extends AbstractWork<T> {

    private List<Predicate<T>> predicates = Collections.emptyList();
    private Work<T> nextIfSucceeded;
    private Work<T> nextIfFailed;
    private ConditionType conditionType = ConditionType.OR;

    public ConditionalWork(Work<T> nextIfSucceeded, Work<T> nextIfFailed, List<Predicate<T>> predicates) {
        this.predicates = predicates;
        this.nextIfSucceeded = nextIfSucceeded;
        this.nextIfFailed = nextIfFailed;
    }


    @SafeVarargs
    public ConditionalWork(Work<T> nextIfSucceeded, Work<T> nextIfFailed, Predicate<T>... predicates) {
        this(nextIfSucceeded, nextIfFailed, Arrays.asList(predicates));
    }

    public ConditionalWork() {
        //Nothing to do
    }

    public List<Predicate<T>> getPredicates() {
        return predicates;
    }

    public void setPredicates(List<Predicate<T>> predicates) {
        this.predicates = predicates;
    }

    public Work<T> getNextIfSucceeded() {
        return nextIfSucceeded;
    }

    public void setNextIfSucceeded(Work<T> nextIfSucceeded) {
        this.nextIfSucceeded = nextIfSucceeded;
    }

    public Work<T> getNextIfFailed() {
        return nextIfFailed;
    }

    public void setNextIfFailed(Work<T> nextIfFailed) {
        this.nextIfFailed = nextIfFailed;
    }

    public ConditionType getConditionType() {
        return conditionType;
    }

    public void setConditionType(ConditionType conditionType) {
        this.conditionType = conditionType;
    }

    @Override
    public void init() throws IOException {
        for (Predicate<T> predicate : predicates) {
            predicate.init();
        }
        if (nextIfSucceeded != null) {
            nextIfSucceeded.init();
        }
        if (nextIfFailed != null) {
            nextIfFailed.init();
        }
    }

    @Override
    public void call(T path) throws IOException {
        boolean result = false;
        for (Predicate<T> predicate : predicates) {
            boolean current = predicate.call(path);
            result = current;
            if ((conditionType == ConditionType.OR && current) ||
                    (conditionType == ConditionType.AND && !current)) {
                break;
            }
        }
        Work<T> next = result ? nextIfSucceeded : nextIfFailed;
        if (next != null) {
            next.call(path);
        }
    }

    @Override
    public void close() throws IOException {
        close(nextIfFailed);
        close(nextIfSucceeded);
        for (Predicate<T> predicate : predicates) {
            predicate.close();
        }
    }

    private void close(Work<T> action) throws IOException {
        if (action != null)
            action.close();
    }
}
