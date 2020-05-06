package com.airgap.airgapagent.domain;

/**
 * com.airgap.airgapagent.domain
 * Created by Jacques Fontignie on 5/5/2020.
 */
public class Scan {

    private Long id;
    private Long repoId;

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


}
