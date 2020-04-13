package com.airgap.airgapagent.flows.workflow;

import com.airgap.airgapagent.flows.work.*;

import java.util.UUID;

/**
 * com.airgap.airgapagent.flows.workflow
 * Created by Jacques Fontignie on 4/12/2020.
 */
public class RepeatFlow extends AbstractWorkFlow {
    private Work work;
    private WorkReportPredicate predicate;

    RepeatFlow(String name, Work work, WorkReportPredicate predicate) {
        super(name);
        this.work = work;
        this.predicate = predicate;
    }

    /**
     * {@inheritDoc}
     */
    public WorkReport call(WorkContext workContext) {
        WorkReport workReport;
        do {
            workReport = work.call(workContext);
        } while (predicate.apply(workReport));
        return workReport;
    }

    public static class Builder {

        private String name;
        private Work work;
        private WorkReportPredicate predicate;

        private Builder() {
            this.name = UUID.randomUUID().toString();
            this.work = new NoOpWork();
            this.predicate = WorkReportPredicate.ALWAYS_FALSE;
        }

        public static RepeatFlow.Builder aNewRepeatFlow() {
            return new RepeatFlow.Builder();
        }

        public RepeatFlow.Builder named(String name) {
            this.name = name;
            return this;
        }

        public RepeatFlow.Builder repeat(Work work) {
            this.work = work;
            return this;
        }

        public RepeatFlow.Builder times(int times) {
            return until(WorkReportPredicate.TimesPredicate.times(times));
        }

        public RepeatFlow.Builder until(WorkReportPredicate predicate) {
            this.predicate = predicate;
            return this;
        }

        public RepeatFlow build() {
            return new RepeatFlow(name, work, predicate);
        }
    }
}

