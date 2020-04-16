package com.airgap.airgapagent.files;

import com.airgap.airgapagent.watch.FileMetadata;
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
    public void testRelativize() {
        Path path = Path.of("src/test/resources");
        System.out.println(path);

        Path second = Path.of("src/test");

        System.out.println(path.relativize(second));

        Assertions.assertEquals("resources", second.relativize(path).toString());
    }

    @Test
    void serialize() throws IOException {
        FileMetadata metadata = new FileMetadata(Path.of("src/test/resources/"),
                Path.of("src/test/resources/sample/sample.txt"));
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        String value = objectMapper.writeValueAsString(metadata);
        System.out.println(value);
        FileMetadata deserialized = objectMapper.readValue(value, FileMetadata.class);
        String secondValue = objectMapper.writeValueAsString(deserialized);
        Assertions.assertEquals(value, secondValue);
    }
}
