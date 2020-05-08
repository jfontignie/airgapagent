package com.airgap.airgapagent.repository;

import com.airgap.airgapagent.domain.Repo;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * com.airgap.airgapagent.repository
 * Created by Jacques Fontignie on 5/8/2020.
 */
public interface RepoRepository extends CrudRepository<Repo, Long> {

    List<Repo> findByPath(String path);
}
