package com.airgap.airgapagent.flows.workflow;

import com.airgap.airgapagent.flows.work.Work;
import com.airgap.airgapagent.flows.work.WorkContext;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;

/**
 * com.airgap.airgapagent.flows.workflow
 * Created by Jacques Fontignie on 4/12/2020.
 */
public class SequentialFlowTest {
    @Test
    public void call() {
        // given
        Work work1 = Mockito.mock(Work.class);
        Work work2 = Mockito.mock(Work.class);
        Work work3 = Mockito.mock(Work.class);
        WorkContext workContext = Mockito.mock(WorkContext.class);
        SequentialFlow sequentialFlow = SequentialFlow.Builder.aNewSequentialFlow()
                .named("testFlow")
                .execute(work1)
                .execute(work2)
                .execute(work3)
                .build();

        // when
        sequentialFlow.call(workContext);

        // then
        InOrder inOrder = Mockito.inOrder(work1, work2, work3);
        inOrder.verify(work1, Mockito.times(1)).call(workContext);
        inOrder.verify(work2, Mockito.times(1)).call(workContext);
        inOrder.verify(work3, Mockito.times(1)).call(workContext);
    }

}
