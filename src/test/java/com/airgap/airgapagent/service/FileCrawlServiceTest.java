package com.airgap.airgapagent.service;

import com.airgap.airgapagent.utils.ConstantsTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.File;
import java.util.Collections;

/**
 * com.airgap.airgapagent.service
 * Created by Jacques Fontignie on 6/1/2020.
 */
class FileCrawlServiceTest {


    private FileCrawlService service;

    @BeforeEach
    void setUp() {
        ContentReaderService contentReader = Mockito.mock(ContentReaderService.class);
        service = new FileCrawlService(contentReader);

    }

    @Test
    void getContentReader() {

        Assertions.assertTrue(service.getContentReader(new File("aa")).isEmpty());
    }

    @Test
    void isLeaf() {
        Assertions.assertTrue(service.isLeaf(ConstantsTest.SAMPLE_CSV));
        Assertions.assertFalse(service.isLeaf(new File("src/test/resources/sample")));
    }

    @Test
    void listChildren() {
        Assertions.assertEquals(Collections.emptyList(), service.listChildren(new File("src/test/resources/empty")));
        Assertions.assertEquals(Collections.singletonList(new File("src/test/resources/sample/files/encrypted.pptx")), service.listChildren(new File("src/test/resources/sample/files")));
    }

    @Test
    void buildLocation() {
        File target = service.buildLocation(new File("root"), new File("root/test/a/b"), new File("target"));
        Assertions.assertEquals(new File("target/test/a/b"), target);
    }
}