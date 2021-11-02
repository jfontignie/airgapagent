package com.airgap.airgapagent.service;

import com.airgap.airgapagent.configuration.CopyOption;
import com.airgap.airgapagent.service.file.FileCrawlService;
import com.airgap.airgapagent.utils.ConstantsTest;
import com.airgap.airgapagent.utils.DataReader;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mock.env.MockEnvironment;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

/**
 * com.airgap.airgapagent.service
 * Created by Jacques Fontignie on 6/1/2020.
 */
class FileCrawlServiceTest {


    private FileCrawlService service;

    @BeforeEach
    void setUp() throws IOException {
        ContentReaderService contentReader = Mockito.mock(ContentReaderService.class);
        Mockito.doReturn(new DataReader<>("test", new HashMap<>(), null)).when(contentReader).getContent(Mockito.any());
        service = new FileCrawlService(contentReader, new ErrorServiceImpl(new MockEnvironment()
                .withProperty(ErrorServiceImpl.ERROR_FILE, "target/error.dat")));

    }

    @Test
    void getContentReader() {
        Assertions.assertNotNull(service.getContentReader(new File("aa")));
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

    @Test
    void ensureCopyWorks() throws IOException {
        File dir = new File("target/ensureCopyWorks");
        FileUtils.forceMkdir(dir);
        File root = new File("src/test/resources/sample");

        File source = new File("src/test/resources/sample/sample.7z");
        service.copy(root, source, dir);
        Assertions.assertTrue(new File("target/ensureCopyWorks/sample.7z").exists());
        service.copy(root, source, dir, Set.of(CopyOption.RENAME_ON_COLLISION));
        File[] files = dir.listFiles();
        Assertions.assertNotNull(files);
        Assertions.assertEquals(2, files.length);


        FileUtils.deleteDirectory(dir);
    }
}
