package com.airgap.airgapagent.service.syslog;

import java.util.Map;
import java.util.StringJoiner;

/**
 * com.airgap.airgapagent.service
 * Created by Jacques Fontignie on 6/6/2020.
 */
public class SyslogFormatter {

    private final StringJoiner stringJoiner = new StringJoiner(" ");

    public SyslogFormatter() {

    }

    public SyslogFormatter(Map<String, String> map) {
        this();
        add(map);
    }

    public void add(String key, String value) {
        stringJoiner.add("\"" + key + "\"=\"" + value + "\"");
    }

    public void add(Map<String, String> map) {
        map.forEach(this::add);
    }

    public String toString() {
        return stringJoiner.toString();
    }
}
