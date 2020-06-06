package com.airgap.airgapagent.utils;

import org.springframework.lang.NonNull;

import java.io.Reader;
import java.util.Map;

/**
 * com.airgap.airgapagent.utils
 * Created by Jacques Fontignie on 5/31/2020.
 */
public class DataReader<T> {

    private final T source;
    private final Map<String, String> metadata;

    private final Reader reader;

    public DataReader(@NonNull T source, @NonNull Map<String, String> metadata, Reader reader) {
        this.source = source;
        this.reader = reader;
        this.metadata = metadata;
    }

    public T getSource() {
        return source;
    }

    public Reader getReader() {
        return reader;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }
}
