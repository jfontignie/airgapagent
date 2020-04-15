package com.airgap.airgapagent.flows.trigger;

import com.airgap.airgapagent.files.FileUtils;
import com.airgap.airgapagent.files.FileWatcher;
import com.airgap.airgapagent.files.PathEvent;
import com.airgap.airgapagent.flows.work.DefaultWorkReport;
import com.airgap.airgapagent.flows.work.WorkStatus;
import com.airgap.airgapagent.flows.workflow.SequentialFlow;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Set;

/**
 * com.airgap.airgapagent.flows.trigger
 * Created by Jacques Fontignie on 4/14/2020.
 */
class FileTriggerTest {

    @Test
    void getName() {
        Assertions.assertNotNull(FileTrigger.Builder.aNewFileTrigger().build().getName());
    }

    @Disabled
    @Test
    public void testTrigger() throws IOException, InterruptedException {
        Trigger trigger = FileTrigger.Builder.aNewFileTrigger()
                .setWatcher(
                        FileWatcher.Builder
                                .aNewWatch(Path.of("target/"))
                                .build()
                ).then(
                        SequentialFlow.Builder
                                .aNewSequentialFlow()
                                .build())
                .build();

        trigger.init();
        Assertions.assertFalse(trigger.poll());

        File f = new File("Target/testTrigger" + FileUtils.buildTimestamp());
        Assertions.assertTrue(f.createNewFile());
        Assertions.assertTrue(f.exists());

        trigger.take();
    }

    @Test
    @Disabled
    void testLong() throws IOException, InterruptedException {
        Trigger trigger = FileTrigger.Builder.aNewFileTrigger()
                .setWatcher(
                        FileWatcher.Builder
                                .aNewWatch(Path.of("target/"))
                                .build()
                ).then(
                        SequentialFlow.Builder
                                .aNewSequentialFlow()
                                .execute(workContext -> {
                                    ((Set<PathEvent>) workContext.get(FileTrigger.FILES_KEY))
                                            .forEach(p ->
                                                    System.out.println(p.toString()));
                                    return new DefaultWorkReport(WorkStatus.COMPLETED, workContext);
                                })
                                .build())
                .build();

        trigger.init();
        //noinspection InfiniteLoopStatement
        while (true) {
            trigger.take();
        }
    }
}
