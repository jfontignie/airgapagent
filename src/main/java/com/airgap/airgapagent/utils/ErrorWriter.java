package com.airgap.airgapagent.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringJoiner;

/**
 * com.airgap.airgapagent.utils
 * Created by Jacques Fontignie on 5/30/2020.
 */
public class ErrorWriter implements Closeable {

    private static final Logger log = LoggerFactory.getLogger(ErrorWriter.class);
    private final FileWriter fileWriter;

    public ErrorWriter(File file) throws IOException {
        fileWriter = new FileWriter(file);
    }

    public void trigger(String source, Throwable t) {
        String expand = ExceptionUtils.expand(t);
        log.error("Error while reading {} - {}", source, expand);
        try {
            fileWriter.write(
                    new StringJoiner(";", "", "\n")
                            .add(source)
                            .add(expand)
                            .toString());
            fileWriter.flush();
        } catch (IOException ioException) {
            log.error("Error wile saving data");
        }
    }

    @Override
    public void close() throws IOException {
        fileWriter.close();
    }
}
