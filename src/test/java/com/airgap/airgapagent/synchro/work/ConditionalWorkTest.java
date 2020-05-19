package com.airgap.airgapagent.synchro.work;

import com.airgap.airgapagent.synchro.predicate.Predicate;
import com.airgap.airgapagent.synchro.utils.PathInfo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.Collections;
import java.util.Objects;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.never;

/**
 * com.airgap.airgapagent.synchro.work
 * Created by Jacques Fontignie on 4/26/2020.
 */
class ConditionalWorkTest {

    private final ConditionalWork<PathInfo> work = new ConditionalWork<>();


    @Mock
    private Predicate<PathInfo> predicate;

    @Mock
    private Work<PathInfo> failedWork;

    @Mock
    private Work<PathInfo> successWork;

    @BeforeEach
    public void setUp() throws IOException {

        MockitoAnnotations.initMocks(this);
        Objects.requireNonNull(predicate);
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
        work.close();
        Mockito.verify(successWork, atLeastOnce()).close();
    }
}
