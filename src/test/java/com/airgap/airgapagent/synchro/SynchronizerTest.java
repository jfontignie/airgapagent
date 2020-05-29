package com.airgap.airgapagent.synchro;

import com.airgap.airgapagent.synchro.predicate.RegexPredicate;
import com.airgap.airgapagent.synchro.work.ConditionalWork;
import com.airgap.airgapagent.synchro.work.CopyWork;
import com.airgap.airgapagent.synchro.work.SequentialWork;
import com.airgap.airgapagent.synchro.work.SyslogWork;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * com.airgap.airgapagent.synchro
 * Created by Jacques Fontignie on 4/17/2020.
 */
class SynchronizerTest {


    @Test
    void testSerialize() throws JsonProcessingException {
        Synchronizer synchronizer = new SynchronizerBuilder().createSynchronizer();
        synchronizer.setBaseFolder("src/test/resources");
        synchronizer.setFlow(new CopyWork("target/sample"));
        YAMLFactory jf = new YAMLFactory();
        jf.disable(YAMLGenerator.Feature.USE_NATIVE_TYPE_ID);
        ObjectMapper objectMapper = new ObjectMapper(jf);
        String value = objectMapper.writeValueAsString(synchronizer);
        System.out.println(value);
        Synchronizer deserialized = objectMapper.readValue(value, Synchronizer.class);
        String secondValue = objectMapper.writeValueAsString(deserialized);
        Assertions.assertEquals(value, secondValue);
    }

    @Test
    void testFullSerialize() throws JsonProcessingException {
        Synchronizer synchronizer = new SynchronizerBuilder()
                .setBaseFolder("set/test/resources")
                .setWork(new SequentialWork<>(
                        new CopyWork("target/sample"),
                        new ConditionalWork<>(
                                new SequentialWork<>(new SyslogWork<>(),
                                        new CopyWork("target/shadow")),
                                null,
                                new RegexPredicate(List.of("pwd", "password"), true))
                ))
                .createSynchronizer();

        YAMLFactory jf = new YAMLFactory();
        jf.disable(YAMLGenerator.Feature.USE_NATIVE_TYPE_ID);
        ObjectMapper objectMapper = new ObjectMapper(jf);
        String value = objectMapper.writeValueAsString(synchronizer);
        System.out.println(value);
        Synchronizer deserialized = objectMapper.readValue(value, Synchronizer.class);
        String secondValue = objectMapper.writeValueAsString(deserialized);
        Assertions.assertEquals(value, secondValue);
    }


    @Test
    void testFull() throws IOException {

        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        Synchronizer deserialized = objectMapper.readValue(new File("src/test/resources/yaml/1_synchro.yaml"), Synchronizer.class);
        Assertions.assertNotNull(deserialized);
        deserialized.run();

    }

}
