package com.airgap.airgapagent.flows.work;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * com.airgap.airgapagent.flows.work
 * Created by Jacques Fontignie on 4/12/2020.
 */
public class WorkContext {
    private Map<String, Object> context = new HashMap<>();

    public void put(String key, Object value) {
        context.put(key, value);
    }

    public Object get(String key) {
        return context.get(key);
    }

    public Set<Map.Entry<String, Object>> getEntrySet() {
        return context.entrySet();
    }

}
