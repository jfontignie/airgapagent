package com.airgap.airgapagent.soar;

/**
 * com.airgap.airgapagent.soar
 * Created by Jacques Fontignie on 5/23/2020.
 */
public interface SyslogService {

    void syslog(String message, Object file);
}
