package com.airgap.airgapagent.utils;

/**
 * com.airgap.airgapagent.utils
 * Created by Jacques Fontignie on 5/31/2020.
 */
public class ObjectStateConverter implements StateConverter<Object> {

    private static final ObjectStateConverter INSTANCE = new ObjectStateConverter();

    private ObjectStateConverter() {
        //Nothing to do
    }

    public static ObjectStateConverter of() {
        return INSTANCE;
    }

    @Override
    public String persist(Object t) {
        return t.toString();
    }

    @Override
    public Object load(String s) {
        throw new IllegalStateException("Impossible to retrieve object");
    }
}
