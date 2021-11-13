package com.airgap.airgapagent;

import com.airgap.airgapagent.configuration.AbstractScanConfiguration;

import java.util.function.Consumer;

/**
 * com.airgap.airgapagent
 * Created by Jacques Fontignie on 11/13/2021.
 */
public interface Runner<T> {

    void run(AbstractScanConfiguration<T> configuration, Consumer<AbstractScanConfiguration<T>> consumer);
}
