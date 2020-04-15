package com.airgap.airgapagent.files;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;

/**
 * com.airgap.airgapagent.files
 * Created by Jacques Fontignie on 4/15/2020.
 */
class FileMetadataTest {
    @Test
    void serialize() throws IOException {
        FileMetadata metadata = new FileMetadata(Path.of("src/test/resources/sample/sample.txt"));
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        String value = objectMapper.writeValueAsString(metadata);
        System.out.println(value);
        FileMetadata deserialized = objectMapper.readValue(value, FileMetadata.class);
        String secondValue = objectMapper.writeValueAsString(deserialized);
        Assertions.assertEquals(value, secondValue);
    }
}
