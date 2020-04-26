package com.airgap.airgapagent.synchro;

import com.airgap.airgapagent.synchro.utils.PathInfo;
import com.airgap.airgapagent.synchro.work.SyslogWork;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

/**
 * com.airgap.airgapagent.synchro
 * Created by Jacques Fontignie on 4/18/2020.
 */
class SyslogWorkTest {

    @Test
    void call() throws IOException {

        SyslogWork task = new SyslogWork();
        task.setMessage("Info");
        task.setPort(514);
        task.setTarget("localhost");
        task.setName("name");


        task.init();
        task.call(new PathInfo("src/test/resources", "sample/sample.txt"));
        Assertions.assertTrue(true);
    }
}
