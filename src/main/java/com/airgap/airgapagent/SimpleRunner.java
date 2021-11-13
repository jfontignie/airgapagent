package com.airgap.airgapagent;

import com.airgap.airgapagent.configuration.AbstractScanConfiguration;

import java.util.function.Consumer;

/**
 * com.airgap.airgapagent
 * Created by Jacques Fontignie on 11/13/2021.
 */
public class SimpleRunner<T> implements Runner<T> {
    @Override
    public void run(AbstractScanConfiguration<T> configuration, Consumer<AbstractScanConfiguration<T>> consumer) {
        consumer.accept(configuration);
    }
}
