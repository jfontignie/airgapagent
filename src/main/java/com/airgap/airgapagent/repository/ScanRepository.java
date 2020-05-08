package com.airgap.airgapagent.repository;

import com.airgap.airgapagent.domain.Repo;
import com.airgap.airgapagent.domain.Scan;
import com.airgap.airgapagent.domain.VisitState;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * com.airgap.airgapagent.repository
 * Created by Jacques Fontignie on 5/8/2020.
 */
public interface ScanRepository extends CrudRepository<Scan, Long> {


    @Query("select s from Scan s, Visit v where s.repo=:repo and v.scan=s and v.state=:state")
    List<Scan> findRunningScanByRepo(Repo repo, VisitState state);

}
