package com.airgap.airgapagent.synchro;

import com.cloudbees.syslog.Severity;
import com.cloudbees.syslog.sender.UdpSyslogMessageSender;

import java.io.IOException;
import java.util.Objects;

/**
 * com.airgap.airgapagent.synchro
 * Created by Jacques Fontignie on 4/17/2020.
 */
public class SyslogTask extends AbstractTask {
    private String target;
    private int port;
    private String message;
    private UdpSyslogMessageSender syslog;

    public SyslogTask() {
        super(TaskType.SYSLOG);

    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public void init() {
        //Nothing to do
        syslog = new UdpSyslogMessageSender();
        syslog.setSyslogServerHostname(target);
        syslog.setSyslogServerPort(port);
        syslog.setDefaultAppName("Synchro");
        syslog.setDefaultSeverity(Severity.ALERT);
    }

    @Override
    public void call(PathInfo path) throws IOException {
        Objects.requireNonNull(syslog, "Init not called");
        syslog.sendMessage(message + ":" + path.getRelative());
    }
}
