package com.airgap.airgapagent.configuration;

import com.airgap.airgapagent.utils.ConstantsTest;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * com.airgap.airgapagent.configuration
 * Created by Jacques Fontignie on 6/4/2020.
 */
class FileCopyActionTest {

    private JCommander getCommander() {
        return JCommander.newBuilder()
                .addCommand(new FileCopyAction())
                .build();
    }


    @SuppressWarnings("java:S5778")
    @Test
    void build() {
        StringBuilder stringBuilder = new StringBuilder();
        getCommander().getUsageFormatter().usage(stringBuilder, "\t");
        System.out.println(stringBuilder.toString());

        FileCopyAction command = new FileCopyAction();
        JCommander commander = JCommander.newBuilder()
                .addCommand(command)
                .build();
        commander.parse("copy", "-minHit", "5",
                "-folder", "src",
                "-target", "src",
                "-corpus", ConstantsTest.CORPUS_SAMPLE_STRING,
                "-options", CopyOption.CLEAN_ON_STARTUP.toString() + "," + CopyOption.FLAT_HIERARCHY.toString());

        Assertions.assertEquals(2, command.getCopyOptions().size());

        Assertions.assertThrows(ParameterException.class, () -> getCommander().parse("copy", "-minHit", "5",
                "-folder", "src",
                "-target", "src",
                "-corpus", ConstantsTest.CORPUS_SAMPLE_STRING,
                "-options", "dsafsf"));

    }

}
