package com.airgap.airgapagent.service;

import com.airgap.airgapagent.utils.ConstantsTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.util.Optional;

/**
 * com.airgap.airgapagent.service
 * Created by Jacques Fontignie on 5/29/2020.
 */
class ContentReaderServiceTest {

    @Test
    void testFile() throws IOException {
        ContentReaderService contentReaderService = new ContentReaderService(new DummyErrorService());
        Assertions.assertTrue(contentReaderService.getContent(new File("does not exits")).isEmpty());

        Optional<Reader> found = contentReaderService.getContent(new File("src/test/resources/sample/sample.txt"));
        Assertions.assertTrue(found.isPresent());
        Assertions.assertTrue(contentReaderService.getContent(ConstantsTest.SAMPLE_CSV).isPresent());

        found.get().close();
    }
}
