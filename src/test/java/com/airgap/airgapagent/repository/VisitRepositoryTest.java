package com.airgap.airgapagent.repository;

import com.airgap.airgapagent.domain.Visit;
import com.airgap.airgapagent.domain.VisitState;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

/**
 * com.airgap.airgapagent.repository
 * Created by Jacques Fontignie on 5/7/2020.
 */
class VisitRepositoryTest {

    private VisitRepository visitRepository;

    @BeforeEach
    public void setUp() {
        visitRepository = new VisitRepository(JdbcTemplateHelper.createFullJdbcTemplate());
    }

    @Test
    void save() {
        Visit visit = new Visit();
        visit.setPath(Path.of("path"));
        visit.setScanId(1L);
        visit.setState(VisitState.VISITED);
        visitRepository.save(visit);
        Assertions.assertNotNull(visit.getId());
        Long id = visit.getId();
        visitRepository.save(visit);
        Assertions.assertEquals(id, visit.getId());
    }

    @Test
    void remove() {
        Visit visit = new Visit();
        visit.setPath(Path.of("path"));
        visit.setScanId(1L);
        visit.setState(VisitState.VISITED);
        visitRepository.save(visit);
        visitRepository.remove(visit);
        Assertions.assertNull(visit.getId());

    }

}
