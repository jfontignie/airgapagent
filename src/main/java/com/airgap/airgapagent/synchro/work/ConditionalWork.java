package com.airgap.airgapagent.synchro.work;

import com.airgap.airgapagent.synchro.predicate.Predicate;
import com.airgap.airgapagent.synchro.utils.PathInfo;
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
public class ConditionalWork extends AbstractWork {

    private List<Predicate> predicates = Collections.emptyList();
    private Work nextIfSucceeded;
    private Work nextIfFailed;
    private ConditionType conditionType = ConditionType.OR;

    public ConditionalWork(Work nextIfSucceeded, Work nextIfFailed, List<Predicate> predicates) {
        this.predicates = predicates;
        this.nextIfSucceeded = nextIfSucceeded;
        this.nextIfFailed = nextIfFailed;
    }

    public ConditionalWork(Work nextIfSucceeded, Work nextIfFailed, Predicate... predicates) {
        this(nextIfSucceeded, nextIfFailed, Arrays.asList(predicates));
    }

    public ConditionalWork() {
        //Nothing to do
    }

    public List<Predicate> getPredicates() {
        return predicates;
    }

    public void setPredicates(List<Predicate> predicates) {
        this.predicates = predicates;
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

    public ConditionType getConditionType() {
        return conditionType;
    }

    public void setConditionType(ConditionType conditionType) {
        this.conditionType = conditionType;
    }

    @Override
    public void init() throws IOException {
        for (Predicate predicate : predicates) {
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
    public void call(PathInfo path) throws IOException {
        boolean result = false;
        for (Predicate predicate : predicates) {
            boolean current = predicate.call(path);
            result = current;
            if ((conditionType == ConditionType.OR && current) ||
                    (conditionType == ConditionType.AND && !current)) {
                break;
            }
        }
        Work next = result ? nextIfSucceeded : nextIfFailed;
        if (next != null) {
            next.call(path);
        }
    }

    @Override
    public void close() throws IOException {
        close(nextIfFailed);
        close(nextIfSucceeded);
        for (Predicate predicate : predicates) {
            predicate.close();
        }
    }

    private void close(Work action) throws IOException {
        if (action != null)
            action.close();
    }
}
