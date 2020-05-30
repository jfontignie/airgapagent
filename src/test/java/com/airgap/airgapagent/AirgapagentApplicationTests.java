package com.airgap.airgapagent;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest()
@TestPropertySource(locations = "file:src/test/resources/test.properties")
class AirgapagentApplicationTests {

    @Test
    void contextLoads() {
        Assertions.assertTrue(true);
    }

}
