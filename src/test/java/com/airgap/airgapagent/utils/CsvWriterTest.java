package com.airgap.airgapagent.utils;

import com.airgap.airgapagent.domain.ExactMatchingResult;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * com.airgap.airgapagent.service
 * Created by Jacques Fontignie on 5/30/2020.
 */
class CsvWriterTest {

    @Test
    void save() throws IOException {

        File file = new File("target/CsvWriterTest.csv");
        Files.deleteIfExists(file.toPath());
        CsvWriter writer = CsvWriter.of(file);
        writer.save(new ExactMatchingResult(new File("teaf"), 5));
        Assertions.assertTrue(file.exists());
        writer.close();
        Assertions.assertTrue(file.exists());
        Files.deleteIfExists(file.toPath());
    }
}
