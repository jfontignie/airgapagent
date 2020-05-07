package com.airgap.airgapagent.synchro.work;

import java.io.IOException;
import java.util.UUID;

/**
 * com.airgap.airgapagent.synchro.work
 * Created by Jacques Fontignie on 5/7/2020.
 */
public abstract class AbstractWork implements Work {

    private String name = UUID.randomUUID().toString();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void close() throws IOException {
        //Nothing to do
    }

    @Override
    public void init() throws IOException {
        //Nothing to do
    }
}
