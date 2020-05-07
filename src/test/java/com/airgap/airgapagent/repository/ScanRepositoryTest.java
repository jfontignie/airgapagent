package com.airgap.airgapagent.repository;

import com.airgap.airgapagent.domain.Repo;
import com.airgap.airgapagent.domain.Scan;
import com.airgap.airgapagent.domain.VisitState;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * com.airgap.airgapagent.repository
 * Created by Jacques Fontignie on 5/7/2020.
 */
class ScanRepositoryTest {

    private ScanRepository scanRepository;

    @BeforeEach
    public void setUp() {
        JdbcTemplate jdbcTemplate = JdbcTemplateHelper.createFullJdbcTemplate();
        this.scanRepository = new ScanRepository(jdbcTemplate);
    }

    @Test
    void save() {
        Scan scan = new Scan();
        scan.setRepoId(1L);
        scanRepository.save(scan);
        Assertions.assertNotNull(scan.getId());
        Long value = scan.getId();
        scanRepository.save(scan);
        Assertions.assertEquals(value, scan.getId());
    }

    @Test
    void remove() {
        Scan scan = new Scan();
        scan.setRepoId(1L);
        scanRepository.save(scan);
        scanRepository.remove(scan);
        Assertions.assertNull(scan.getId());
    }

    @Test
    void findByRepoIdAndState() {

        Repo repo = new Repo();
        repo.setId(101L);
        Scan found = scanRepository.findByRepoIdAndState(repo, VisitState.VISITED);
        Assertions.assertEquals(110L, found.getId());
    }
}
