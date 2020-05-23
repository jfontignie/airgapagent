package com.airgap.airgapagent.service;


import com.airgap.airgapagent.domain.Identifiable;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;

/**
 * com.airgap.airgapagent.service
 * Created by Jacques Fontignie on 5/23/2020.
 */
public interface StoreService {

    <T extends Identifiable>
    Optional<T> store(String flow, T t, Map<String, Instant> timestamp);


    <T extends Identifiable, S>
    S get(T t, String timestamp, S defaultValue);
}
