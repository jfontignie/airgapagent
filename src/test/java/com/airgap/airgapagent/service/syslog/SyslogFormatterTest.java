package com.airgap.airgapagent.service.syslog;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

/**
 * com.airgap.airgapagent.service.syslog
 * Created by Jacques Fontignie on 11/3/2021.
 */
class SyslogFormatterTest {


    @Test
    void testToString() {
        SyslogFormatter formatter = new SyslogFormatter(Map.of("v1", "1"));
        formatter.add(Map.of("v2", "2"));
        Assertions.assertThat(formatter.toString()).hasToString("\"v1\"=\"1\" \"v2\"=\"2\"");
    }
}
