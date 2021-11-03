package com.airgap.airgapagent.utils;

/**
 * com.airgap.airgapagent.utils
 * Created by Jacques Fontignie on 5/31/2020.
 */
public class ObjectSerializer implements Serializer<Object> {

    private static final ObjectSerializer INSTANCE = new ObjectSerializer();

    private ObjectSerializer() {
        //Nothing to do
    }

    public static ObjectSerializer of() {
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
