package com.airgap.airgapagent.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;

/**
 * com.airgap.airgapagent.service
 * Created by Jacques Fontignie on 5/9/2020.
 */
class FileWrapperServiceTest {

    private final FileWrapperService fileWrapperService = new FileWrapperService();

    @Test
    void isRegularFile() {
        Assertions.assertTrue(fileWrapperService.isRegularFile(Path.of("src/test/resources/sample/sample.docx")));

        Assertions.assertFalse(fileWrapperService.isRegularFile(Path.of("src/test/resources/sample")));
    }

    @Test
    void list() throws IOException {
        Assertions.assertEquals(2, fileWrapperService.list(Path.of("src/test/resources/sample/folder")).size());
    }

    @Test
    void getLastModifiedTime() throws IOException {
        FileTime fileTime = fileWrapperService.getLastModifiedTime(Path.of("src/test/resources/sample/sample.docx"));
        Assertions.assertNotNull(fileTime);
    }

}
