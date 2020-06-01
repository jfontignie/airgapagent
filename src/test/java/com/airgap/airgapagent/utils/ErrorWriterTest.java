package com.airgap.airgapagent.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * com.airgap.airgapagent.utils
 * Created by Jacques Fontignie on 6/1/2020.
 */
class ErrorWriterTest {

    @Test
    void trigger() throws IOException {

        File file = new File("target/ErrorWriter.csv");
        Files.deleteIfExists(file.toPath());
        ErrorWriter writer = new ErrorWriter(file);
        writer.trigger("source", "message", new IndexOutOfBoundsException());
        writer.trigger(null, "message", new IndexOutOfBoundsException());
        writer.trigger("source", null, new IndexOutOfBoundsException());
        writer.trigger("source", null, null);

        writer.close();
        Assertions.assertTrue(Files.lines(file.toPath()).count() > 0);
        Files.deleteIfExists(file.toPath());
    }
}
