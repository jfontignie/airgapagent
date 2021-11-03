package com.airgap.airgapagent.utils;

import com.airgap.airgapagent.utils.exceptions.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;
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


    public void trigger(Object source, String message, Throwable t) {
        String expand = ExceptionUtils.expand(t);
        log.info("{} - {} - {}", source, message, expand);
        try {
            String str = new StringJoiner(";", "", "\n")
                    .add(Objects.requireNonNullElse(source, "").toString())
                    .add(message)
                    .add(expand)
                    .toString();
            fileWriter.write(
                    str);
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
