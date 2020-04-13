package com.airgap.airgapagent.flows.work;

import org.springframework.lang.NonNull;

import java.util.UUID;

/**
 * com.airgap.airgapagent.flows.work
 * Created by Jacques Fontignie on 4/12/2020.
 */
public interface Work {
    default String getName() {
        return UUID.randomUUID().toString();
    }

    WorkReport call(@NonNull WorkContext workContext);
}
