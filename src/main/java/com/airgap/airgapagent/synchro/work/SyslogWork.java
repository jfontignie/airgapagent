package com.airgap.airgapagent.synchro.work;

import com.airgap.airgapagent.synchro.utils.PathInfo;
import com.cloudbees.syslog.Severity;
import com.cloudbees.syslog.sender.UdpSyslogMessageSender;

import java.io.IOException;
import java.util.Objects;

/**
 * com.airgap.airgapagent.synchro
 * Created by Jacques Fontignie on 4/17/2020.
 */
public class SyslogWork extends AbstractWork {
    private String target;
    private int port;
    private String message;
    private UdpSyslogMessageSender syslog;

    public SyslogWork(String target, int port, String message, String name) {
        this.target = target;
        this.port = port;
        this.message = message;
        setName(name);
    }

    public SyslogWork() {
        //Nothing to do
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
