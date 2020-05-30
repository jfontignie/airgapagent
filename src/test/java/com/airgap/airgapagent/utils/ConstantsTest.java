package com.airgap.airgapagent.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;

/**
 * com.airgap.airgapagent.service
 * Created by Jacques Fontignie on 5/30/2020.
 */
@SuppressWarnings("java:S5786")
public class ConstantsTest {

    public static final String SAMPLE_FOLDER_STRING = "src/test/resources/sample";
    public static final File SAMPLE_FOLDER = new File(SAMPLE_FOLDER_STRING);


    public static final String CORPUS_SAMPLE_STRING = "src/test/resources/sample/bigsample.csv";
    public static final File CORPUS_SAMPLE = new File(CORPUS_SAMPLE_STRING);

    @Test
    void doNothing() {
        //Silly test to avoid warning
        Assertions.assertTrue(true);
    }
}
