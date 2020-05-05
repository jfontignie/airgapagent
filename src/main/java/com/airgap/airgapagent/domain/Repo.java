package com.airgap.airgapagent.domain;

/**
 * com.airgap.airgapagent.domain
 * Created by Jacques Fontignie on 5/5/2020.
 */
public class Repo {

    private Long id;

    private String path;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
