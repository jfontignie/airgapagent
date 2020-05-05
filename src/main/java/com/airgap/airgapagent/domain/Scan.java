package com.airgap.airgapagent.domain;

/**
 * com.airgap.airgapagent.domain
 * Created by Jacques Fontignie on 5/5/2020.
 */
public class Scan {

    private Long id;
    private Long repoId;
    private int sequence;
    private ScanState state;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRepoId() {
        return repoId;
    }

    public void setRepoId(Long repoId) {
        this.repoId = repoId;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public ScanState getState() {
        return state;
    }

    public void setState(ScanState state) {
        this.state = state;
    }
}
