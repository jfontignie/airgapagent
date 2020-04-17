package com.airgap.airgapagent.synchro;

/**
 * com.airgap.airgapagent.synchro
 * Created by Jacques Fontignie on 4/17/2020.
 */
public class SyslogTask extends AbstractTask {
    private String target;
    private int port;

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

    @Override
    public void init() {
        //Nothing to do
    }
}
