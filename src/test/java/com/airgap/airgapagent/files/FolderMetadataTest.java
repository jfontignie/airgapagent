package com.airgap.airgapagent.files;

import com.airgap.airgapagent.watch.FolderMetadata;
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
class FolderMetadataTest {
    @Test
    void serialize() throws IOException {
        FolderMetadata metadata = new FolderMetadata(Path.of("src/test/resources/sample"));
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        String value = objectMapper.writeValueAsString(metadata);
        System.out.println(value);
        FolderMetadata deserialized = objectMapper.readValue(value, FolderMetadata.class);
        String secondValue = objectMapper.writeValueAsString(deserialized);
        Assertions.assertEquals(value, secondValue);
    }
}
