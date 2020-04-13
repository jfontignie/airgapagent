package com.airgap.airgapagent.flows.work;

import java.util.StringJoiner;

/**
 * com.airgap.airgapagent.flows.work
 * Created by Jacques Fontignie on 4/12/2020.
 */
public class DefaultWorkReport implements WorkReport{
    private final WorkStatus status;
    private final WorkContext workContext;
    private Throwable error;

    /**
     * Create a new {@link DefaultWorkReport}.
     *
     * @param status of work
     */
    public DefaultWorkReport(WorkStatus status, WorkContext workContext) {
        this.status = status;
        this.workContext = workContext;
    }

    /**
     * Create a new {@link DefaultWorkReport}.
     *
     * @param status of work
     * @param error if any
     */
    public DefaultWorkReport(WorkStatus status, WorkContext workContext, Throwable error) {
        this(status, workContext);
        this.error = error;
    }

    public WorkStatus getStatus() {
        return status;
    }

    public Throwable getError() {
        return error;
    }

    @Override
    public WorkContext getWorkContext() {
        return workContext;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", DefaultWorkReport.class.getSimpleName() + "[", "]")
                .add("status=" + status)
                .add("workContext=" + workContext)
                .add("error=" + error)
                .toString();
    }
}
