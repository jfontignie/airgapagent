package com.airgap.airgapagent.files;

import com.airgap.airgapagent.watch.SnapshotNode;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * com.airgap.airgapagent.files
 * Created by Jacques Fontignie on 4/15/2020.
 */
class SnapshotNodeTest {

    @Test
    void serialize() throws JsonProcessingException {
        SnapshotNode root = new SnapshotNode(null);
        root.addChild(new SnapshotNode(null));
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        String value = objectMapper.writeValueAsString(root);
        System.out.println(value);
        SnapshotNode deserialized = objectMapper.readValue(value, SnapshotNode.class);
        String secondValue = objectMapper.writeValueAsString(deserialized);
        Assertions.assertEquals(value, secondValue);
    }


}
