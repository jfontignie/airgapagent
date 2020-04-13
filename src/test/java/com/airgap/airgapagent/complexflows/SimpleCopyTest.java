package com.airgap.airgapagent.complexflows;

import com.airgap.airgapagent.files.FileCopyTask;
import com.airgap.airgapagent.flows.work.WorkContext;
import com.airgap.airgapagent.flows.workflow.SequentialFlow;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Path;

/**
 * com.airgap.airgapagent.complexflows
 * Created by Jacques Fontignie on 4/12/2020.
 */
public class SimpleCopyTest {

    @Test
    public void testCopy() {
        new File("target/sample.txt").delete();
        SequentialFlow.Builder.aNewSequentialFlow()
                .execute(
                        FileCopyTask.Builder.aNewFileCopyTask()
                                .setSource(Path.of("src/test/resources/sample/"))
                                .addFileToCopy(Path.of("sample.txt"))
                                .addTarget(Path.of("target/"))
                                .addTarget(Path.of("target/"))
                                .build()
                ).build().call(new WorkContext());
        Assertions.assertThat(Path.of("target/sample.txt")).exists();

    }

}
