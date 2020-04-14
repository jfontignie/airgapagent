package com.airgap.airgapagent.flows.trigger;

import com.airgap.airgapagent.files.FileUtils;
import com.airgap.airgapagent.files.FileWatcher;
import com.airgap.airgapagent.flows.workflow.SequentialFlow;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

/**
 * com.airgap.airgapagent.flows.trigger
 * Created by Jacques Fontignie on 4/14/2020.
 */
class FileTriggerTest {

    @Test
    void getName() {
        Assertions.assertNotNull(FileTrigger.Builder.aNewFileTrigger().build().getName());
    }

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


}
