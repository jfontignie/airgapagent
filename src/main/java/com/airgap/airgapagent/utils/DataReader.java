package com.airgap.airgapagent.utils;

import java.io.Reader;

/**
 * com.airgap.airgapagent.utils
 * Created by Jacques Fontignie on 5/31/2020.
 */
public class DataReader<T> {

    private final T source;

    private final Reader reader;

    public DataReader(T source, Reader reader) {
        this.source = source;
        this.reader = reader;
    }

    public T getSource() {
        return source;
    }

    public Reader getReader() {
        return reader;
    }
}
