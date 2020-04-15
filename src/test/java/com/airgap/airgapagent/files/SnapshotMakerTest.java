package com.airgap.airgapagent.files;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * com.airgap.airgapagent.files
 * Created by Jacques Fontignie on 4/15/2020.
 */
class SnapshotMakerTest {

    @Test
    public void visit() throws IOException {
        SnapshotMaker maker = new SnapshotMaker(Paths.get("src/test/resources/sample/folder/subfolder"));
        SnapshotNode<Metadata> result = maker.visit();
        Assertions.assertEquals("subfolder", result.getData().getFileName());
        Assertions.assertEquals(1, result.getChildren().size());
        SnapshotNode<Metadata> child = result.getChildren().iterator().next();
        Assertions.assertEquals("subfile.txt", child.getData().getFileName());
        System.out.println(result);

        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        String value = objectMapper.writeValueAsString(result);
        System.out.println(value);
        SnapshotNode deserialized = objectMapper.readValue(value, SnapshotNode.class);
        String secondValue = objectMapper.writeValueAsString(deserialized);
        Assertions.assertEquals(value, secondValue);
    }
}
