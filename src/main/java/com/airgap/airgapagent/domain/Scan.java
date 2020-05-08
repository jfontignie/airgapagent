package com.airgap.airgapagent.domain;

import javax.persistence.*;
import java.util.Set;

/**
 * com.airgap.airgapagent.domain
 * Created by Jacques Fontignie on 5/5/2020.
 */
@Entity
public class Scan {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Repo repo;

    @OneToMany
    private Set<Visit> visits;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Repo getRepo() {
        return repo;
    }

    public void setRepo(Repo repo) {
        this.repo = repo;
    }
}
