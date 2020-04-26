package com.airgap.airgapagent.synchro;

/**
 * com.airgap.airgapagent.synchro
 * Created by Jacques Fontignie on 4/26/2020.
 */
public class SnapshotStatus {

    private final long lastTime;

    public SnapshotStatus(long fromMillis) {
        this.lastTime = fromMillis;
    }

    public long getLastTime() {
        return lastTime;
    }
}
