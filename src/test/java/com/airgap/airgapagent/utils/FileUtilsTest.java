package com.airgap.airgapagent.utils;

import com.airgap.airgapagent.utils.file.FileUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

/**
 * com.airgap.airgapagent.utils
 * Created by Jacques Fontignie on 5/3/2020.
 */
class FileUtilsTest {

    @Test
    void withTimeStamp() {
        Path p = Path.of("test.txt");
        Path newPath = FileUtils.withTimeStamp(p);
        Assertions.assertNotEquals(p, newPath);
        System.out.println(newPath);
    }


    @Test
    void withRandomTimeStamp() {
        Path p = Path.of("test.txt");
        Path newPath = FileUtils.withRandomTimeStamp(p);
        Assertions.assertNotEquals(p, newPath);
        System.out.println(newPath);
    }
}
