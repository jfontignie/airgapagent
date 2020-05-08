package com.airgap.airgapagent.domain;

import javax.persistence.*;
import java.util.Set;

/**
 * com.airgap.airgapagent.domain
 * Created by Jacques Fontignie on 5/5/2020.
 */
@Entity
public class Repo {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String path;

    @OneToMany
    private Set<Scan> scans;

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
