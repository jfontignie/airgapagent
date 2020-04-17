package com.airgap.airgapagent.synchro;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Collections;

/**
 * com.airgap.airgapagent.synchro
 * Created by Jacques Fontignie on 4/17/2020.
 */
class SynchronizerTest {

    private static final String SIMPLE = "baseFolder: \"src/test/resources\"\n" +
            "earlierThan: 60\n" +
            "tasks:\n" +
            "- !<COPY>\n" +
            "  name: \"Copy\"\n" +
            "  taskType: \"COPY\"";

    @Test
    void testSerialize() throws JsonProcessingException {
        Synchronizer synchronizer = new Synchronizer();
        synchronizer.setBaseFolder("src/test/resources");
        synchronizer.setEarlierThan(60);
        synchronizer.setTasks(
                Collections.singletonList(new CopyTask("Copy", "target/sample")));
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        String value = objectMapper.writeValueAsString(synchronizer);
        System.out.println(value);
        Synchronizer deserialized = objectMapper.readValue(value, Synchronizer.class);
        String secondValue = objectMapper.writeValueAsString(deserialized);
        Assertions.assertEquals(value, secondValue);
    }

    @Test
    public void testFull() throws IOException {

        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        Synchronizer deserialized = objectMapper.readValue(new File("src/test/resources/yaml/1_synchro.yaml"), Synchronizer.class);
        Assertions.assertNotNull(deserialized);

    }

    @Test
    void simpleDeserialize() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        Synchronizer deserialized = objectMapper.readValue(SIMPLE, Synchronizer.class);
        Assertions.assertNotNull(deserialized);
    }

}
