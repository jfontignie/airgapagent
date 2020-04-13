package com.airgap.airgapagent.flows.work;

import org.springframework.lang.NonNull;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * com.airgap.airgapagent.flows.work
 * Created by Jacques Fontignie on 4/12/2020.
 */
public interface WorkReportPredicate {

    boolean apply(@NonNull WorkReport workReport);

    WorkReportPredicate ALWAYS_TRUE = workReport -> true;
    WorkReportPredicate ALWAYS_FALSE = workReport -> false;
    WorkReportPredicate COMPLETED = workReport -> workReport.getStatus().equals(WorkStatus.COMPLETED);
    WorkReportPredicate FAILED = workReport -> workReport.getStatus().equals(WorkStatus.FAILED);

    class TimesPredicate implements WorkReportPredicate {

        private final int times;

        private final AtomicInteger counter = new AtomicInteger();

        public TimesPredicate(int times) {
            this.times = times;
        }

        @Override
        public boolean apply(@NonNull WorkReport workReport) {
            return counter.incrementAndGet() != times;
        }

        public static TimesPredicate times(int times) {
            return new TimesPredicate(times);
        }
    }

}
