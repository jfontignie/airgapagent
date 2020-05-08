package com.airgap.airgapagent.repository;

import com.airgap.airgapagent.domain.Scan;
import com.airgap.airgapagent.domain.Visit;
import com.airgap.airgapagent.domain.VisitState;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * com.airgap.airgapagent.repository
 * Created by Jacques Fontignie on 5/8/2020.
 */
public interface VisitRepository extends CrudRepository<Visit, Long> {

    @Query("select v from Visit v where " +
            "not v.scan=:scan and v.path=:path and " +
            "v.state=com.airgap.airgapagent.domain.VisitState.VISITED " +
            "order by v.updated desc  ")
    List<Visit> findByScanNotEqualsAndPathEqualsOrderByUpdatedDesc(Scan scan, String path);

    List<Visit> findByScanAndStateOrderByUpdatedDesc(Scan scan, VisitState state);
}
