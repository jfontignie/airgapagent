package com.airgap.airgapagent.synchro;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

/**
 * com.airgap.airgapagent.synchro
 * Created by Jacques Fontignie on 4/18/2020.
 */
class SyslogTaskTest {

    @Test
    void call() throws IOException {

        SyslogTask task = new SyslogTask();
        task.setMessage("Info");
        task.setPort(514);
        task.setTarget("localhost");
        task.setName("name");


        task.init();
        task.call(new PathInfo("src/test/resources", "sample/sample.txt"));
        Assertions.assertTrue(true);
    }
}
