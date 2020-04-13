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
public class RepeatFlowTest {

    @Test
    public void testRepeatUntil() {
        // given
        Work work = Mockito.mock(Work.class);
        WorkContext workContext = Mockito.mock(WorkContext.class);
        WorkReportPredicate predicate = WorkReportPredicate.ALWAYS_FALSE;
        RepeatFlow repeatFlow = RepeatFlow.Builder.aNewRepeatFlow()
                .repeat(work)
                .until(predicate)
                .build();

        // when
        repeatFlow.call(workContext);

        // then
        Mockito.verify(work, Mockito.times(1)).call(workContext);
    }

    @Test
    public void testRepeatTimes() {
        // given
        Work work = Mockito.mock(Work.class);
        WorkContext workContext = Mockito.mock(WorkContext.class);
        RepeatFlow repeatFlow = RepeatFlow.Builder.aNewRepeatFlow()
                .repeat(work)
                .times(3)
                .build();

        // when
        repeatFlow.call(workContext);

        // then
        Mockito.verify(work, Mockito.times(3)).call(workContext);
    }
}
