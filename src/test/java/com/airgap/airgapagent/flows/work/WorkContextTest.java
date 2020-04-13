package com.airgap.airgapagent.flows.work;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * com.airgap.airgapagent.flows.work
 * Created by Jacques Fontignie on 4/12/2020.
 */
class WorkContextTest {

    private static final String KEY = "key";

    @Test
    void put() {
        WorkContext context = new WorkContext();
        int size = context.getEntrySet().size();
        context.put(KEY, null);
        Assertions.assertEquals(
                size+1,
                context.getEntrySet().size());
    }

    @Test
    void get() {

        WorkContext context = new WorkContext();
        int size = context.getEntrySet().size();
        context.put(KEY, 2);
        Assertions.assertEquals(2, context.get(KEY));
    }

    @Test
    void getEntrySet() {
        WorkContext context = new WorkContext();
        int size = context.getEntrySet().size();
        Assertions.assertEquals(0,size);
    }
}
