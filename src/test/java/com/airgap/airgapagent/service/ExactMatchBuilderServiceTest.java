package com.airgap.airgapagent.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Set;

/**
 * com.airgap.airgapagent.service
 * Created by Jacques Fontignie on 5/29/2020.
 */
class ExactMatchBuilderServiceTest {

    @Test
    void build() throws IOException {
        ExactMatchBuilderService service = new ExactMatchBuilderService();
        Set<String> found = service.buildSet(new File("src/test/resources/sample/sample.csv"));
        Assertions.assertTrue(found.contains("password"));
        Assertions.assertTrue(found.contains("abcd"));
        Assertions.assertTrue(found.contains("efgh"));
        Assertions.assertEquals(3, found.size());
    }

    @Test
    void buildTree() throws IOException {
        ExactMatchBuilderService service = new ExactMatchBuilderService();
        Set<String> found = service.buildTreeSet(new File("src/test/resources/sample/sample.csv"));
        Assertions.assertNotNull(found);
    }

    @Test
    void buildBigSample() throws IOException {
        ExactMatchBuilderService service = new ExactMatchBuilderService();
        Set<String> found = service.buildTreeSet(new File("src/test/resources/sample/bigsample.csv"));
        Assertions.assertNotNull(found);

    }

}
