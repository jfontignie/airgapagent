package com.airgap.airgapagent.service;

import com.airgap.airgapagent.service.crawl.ContentReaderService;
import com.airgap.airgapagent.utils.DataReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.NoSuchFileException;

/**
 * com.airgap.airgapagent.service
 * Created by Jacques Fontignie on 5/29/2020.
 */
class ContentReaderServiceTest {

    @Test
    void testFile() throws IOException {
        ContentReaderService contentReaderService = new ContentReaderService();
        Assertions.assertThrows(NoSuchFileException.class, () -> contentReaderService.getContent(new File("does not exits")));

        DataReader<File> found = contentReaderService.getContent(new File("src/test/resources/sample/sample.txt"));
        Assertions.assertNotNull(found.getReader());
        found.getReader().close();
    }


    @Test
    void testWord() throws IOException {
        ContentReaderService contentReaderService = new ContentReaderService();

        DataReader<File> found = contentReaderService.getContent(new File("src/test/resources/sample/sample.docx"));
        Assertions.assertNotNull(found.getReader());
    }


    @Test
    void testMail() throws IOException {
        ContentReaderService contentReaderService = new ContentReaderService();

        DataReader<File> found = contentReaderService.getContent(new File("src/test/resources/sample/sample.eml"));
        Assertions.assertNotNull(found.getReader());
        found.getReader().close();
    }
}
