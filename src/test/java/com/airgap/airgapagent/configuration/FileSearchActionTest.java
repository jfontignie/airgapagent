package com.airgap.airgapagent.configuration;

import com.airgap.airgapagent.utils.ConstantsTest;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;

/**
 * com.airgap.airgapagent.configuration
 * Created by Jacques Fontignie on 6/4/2020.
 */
class FileSearchActionTest {

    private JCommander getCommander() {
        return JCommander.newBuilder()
                .addCommand(new FileSearchConfiguration())
                .build();
    }

    @SuppressWarnings("java:S5778")
    @Test
    void build() {
        StringBuilder stringBuilder = new StringBuilder();
        getCommander().getUsageFormatter().usage(stringBuilder, "\t");
        System.out.println(stringBuilder);


        Assertions.assertThrows(ParameterException.class, () -> getCommander().parse("search"));


        Assertions.assertThrows(ParameterException.class, () ->
                getCommander().parse("search", "-minHit", "5", "-folder", "src2", "-found", "file.csv"));

        FileSearchConfiguration command = new FileSearchConfiguration();
        JCommander commander = JCommander.newBuilder()
                .addCommand(command)
                .build();
        commander.parse("search", "-minHit", "5", "-folder", "src", "-found", "file.csv", "-corpus", ConstantsTest.CORPUS_SAMPLE_STRING);
        Assertions.assertEquals(new File("file.csv"), command.getFoundLocation());

    }

}
