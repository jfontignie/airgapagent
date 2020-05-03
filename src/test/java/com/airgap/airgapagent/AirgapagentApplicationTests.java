package com.airgap.airgapagent;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(args = "--config=src/test/resources/yaml/1_synchro.yaml")
class AirgapagentApplicationTests {

    @Test
    void contextLoads() {
        Assertions.assertTrue(true);
    }

}
