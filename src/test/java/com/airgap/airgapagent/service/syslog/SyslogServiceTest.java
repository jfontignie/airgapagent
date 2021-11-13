package com.airgap.airgapagent.service.syslog;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.core.env.Environment;
import org.springframework.mock.env.MockEnvironment;

import java.io.IOException;

import static com.airgap.airgapagent.service.syslog.SyslogService.SYSLOG_SERVER;

/**
 * com.airgap.airgapagent.service.syslog
 * Created by Jacques Fontignie on 11/7/2021.
 */
class SyslogServiceTest {

    @Test
    void noServer() throws IOException {
        Environment environment = new MockEnvironment();
        SyslogService service = new SyslogService(environment);
        service.send("test");
        Assertions.assertTrue(true);
    }

    @Test
    void send() {
        MockEnvironment environment = new MockEnvironment();
        environment.setProperty(SYSLOG_SERVER, "1234");
        SyslogService service = new SyslogService(environment);
        Assertions.assertTrue(true);
    }

}
