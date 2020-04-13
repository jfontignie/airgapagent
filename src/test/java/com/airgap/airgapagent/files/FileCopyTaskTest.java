package com.airgap.airgapagent.files;

import com.airgap.airgapagent.flows.work.WorkContext;
import com.airgap.airgapagent.flows.work.WorkReport;
import com.airgap.airgapagent.flows.work.WorkStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.NoSuchFileException;
import java.nio.file.Path;

/**
 * com.airgap.airgapagent.files
 * Created by Jacques Fontignie on 4/13/2020.
 */
class FileCopyTaskTest {

    @Test
    void testFolder() {
        FileCopyTask task = FileCopyTask.Builder.aNewFileCopyTask()
                .setSource(Path.of("src/test/resources/sample"))
                .addFileToCopy(Path.of("folder"))
                .addTarget(Path.of("target/"))
                .build();
        WorkReport report = task.call(new WorkContext());
        Assertions.assertEquals(WorkStatus.COMPLETED, report.getStatus());
    }


    @Test
    void testFile() {
        FileCopyTask task = FileCopyTask.Builder.aNewFileCopyTask()
                .setSource(Path.of("src/test/resources/sample"))
                .addFileToCopy(Path.of("sample.txt"))
                .addTarget(Path.of("target/"))
                .build();
        WorkReport report = task.call(new WorkContext());
        Assertions.assertEquals(WorkStatus.COMPLETED, report.getStatus());
    }

    @Test
    void testInvalidTarget() {
        FileCopyTask task = FileCopyTask.Builder.aNewFileCopyTask()
                .setSource(Path.of("src/test/resources/sample"))
                .addFileToCopy(Path.of("sample.txt"))
                .addTarget(Path.of("target2/"))
                .build();
        WorkReport report = task.call(new WorkContext());
        Assertions.assertEquals(WorkStatus.FAILED, report.getStatus());
        Assertions.assertEquals(NoSuchFileException.class, report.getError().getClass());
    }

}
