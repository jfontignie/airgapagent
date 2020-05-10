package com.airgap.airgapagent.synchro.work;

import com.airgap.airgapagent.synchro.predicate.Predicate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.never;

/**
 * com.airgap.airgapagent.synchro.work
 * Created by Jacques Fontignie on 4/26/2020.
 */
class ConditionalWorkTest {

    private final ConditionalWork work = new ConditionalWork();
    private Predicate predicate;
    private Work failedWork;
    private Work successWork;

    @BeforeEach
    public void setUp() throws IOException {
        predicate = Mockito.mock(Predicate.class);
        failedWork = Mockito.mock(Work.class);
        successWork = Mockito.mock(Work.class);
        work.setPredicates(Collections.singletonList(predicate));
        work.setNextIfFailed(failedWork);
        work.setNextIfSucceeded(successWork);

        Assertions.assertEquals(successWork, work.getNextIfSucceeded());
        Assertions.assertEquals(failedWork, work.getNextIfFailed());
        Assertions.assertEquals(predicate, work.getPredicates().get(0));

        work.init();
    }

    @AfterEach
    public void tearDown() throws IOException {
        work.close();
    }


    @Test
    void callFalse() throws IOException {
        Mockito.doReturn(false).when(predicate).call(any());
        work.call(null);
        Mockito.verify(failedWork, atLeastOnce()).call(any());
        Mockito.verify(successWork, never()).call(any());
    }


    @Test
    void callTrue() throws IOException {
        Mockito.doReturn(true).when(predicate).call(any());
        work.call(null);
        Mockito.verify(successWork, atLeastOnce()).call(any());
        Mockito.verify(failedWork, never()).call(any());
    }

    @Test
    void callClose() throws IOException {
        Work successWork = Mockito.mock(Work.class);
        work.setNextIfSucceeded(successWork);
        work.close();
        Mockito.verify(successWork, atLeastOnce()).close();
    }
}
