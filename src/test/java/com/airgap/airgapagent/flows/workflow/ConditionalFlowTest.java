package com.airgap.airgapagent.flows.workflow;

import com.airgap.airgapagent.flows.work.Work;
import com.airgap.airgapagent.flows.work.WorkContext;
import com.airgap.airgapagent.flows.work.WorkReportPredicate;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

/**
 * com.airgap.airgapagent.flows.workflow
 * Created by Jacques Fontignie on 4/12/2020.
 */
public class ConditionalFlowTest {
    @Test
    public void callOnPredicateSuccess() {
        // given
        Work toExecute = Mockito.mock(Work.class);
        Work nextOnPredicateSuccess = Mockito.mock(Work.class);
        Work nextOnPredicateFailure = Mockito.mock(Work.class);
        WorkContext workContext = Mockito.mock(WorkContext.class);
        WorkReportPredicate predicate = WorkReportPredicate.ALWAYS_TRUE;
        ConditionalFlow conditionalFlow = ConditionalFlow.Builder.aNewConditionalFlow()
                .named("testFlow")
                .execute(toExecute)
                .when(predicate)
                .then(nextOnPredicateSuccess)
                .otherwise(nextOnPredicateFailure)
                .build();

        // when
        conditionalFlow.call(workContext);

        // then
        Mockito.verify(toExecute, Mockito.times(1)).call(workContext);
        Mockito.verify(nextOnPredicateSuccess, Mockito.times(1)).call(workContext);
        Mockito.verify(nextOnPredicateFailure, Mockito.never()).call(workContext);
    }

    @Test
    public void callOnPredicateFailure() {
        // given
        Work toExecute = Mockito.mock(Work.class);
        Work nextOnPredicateSuccess = Mockito.mock(Work.class);
        Work nextOnPredicateFailure = Mockito.mock(Work.class);
        WorkContext workContext = Mockito.mock(WorkContext.class);
        WorkReportPredicate predicate = WorkReportPredicate.ALWAYS_FALSE;
        ConditionalFlow conditionalFlow = ConditionalFlow.Builder.aNewConditionalFlow()
                .named("anotherTestFlow")
                .execute(toExecute)
                .when(predicate)
                .then(nextOnPredicateSuccess)
                .otherwise(nextOnPredicateFailure)
                .build();

        // when
        conditionalFlow.call(workContext);

        // then
        Mockito.verify(toExecute, Mockito.times(1)).call(workContext);
        Mockito.verify(nextOnPredicateFailure, Mockito.times(1)).call(workContext);
        Mockito.verify(nextOnPredicateSuccess, Mockito.never()).call(workContext);
    }

}
