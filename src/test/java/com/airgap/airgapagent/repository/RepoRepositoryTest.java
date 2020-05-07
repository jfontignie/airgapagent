package com.airgap.airgapagent.repository;

import com.airgap.airgapagent.domain.Repo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * com.airgap.airgapagent.repository
 * Created by Jacques Fontignie on 5/6/2020.
 */
class RepoRepositoryTest {

    private static final String TO_FIND = "toFind";
    private RepoRepository repoRepository;

    @BeforeEach
    public void setUp() {
        JdbcTemplate jdbcTemplate = JdbcTemplateHelper.createJdbcTemplate();
        repoRepository = new RepoRepository(jdbcTemplate);

    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void save() {
        Repo repo = new Repo();
        repo.setPath("path");
        repoRepository.save(repo);
        Assertions.assertNotNull(repo.getId());
        Long lastId = repo.getId();
        repoRepository.save(repo);
        Assertions.assertEquals(lastId, repo.getId());
    }

    @Test
    public void remove() {
        Repo repo = new Repo();
        repo.setPath("path");
        repoRepository.save(repo);
        repoRepository.remove(repo);
        Assertions.assertNull(repo.getId());
    }


    @Test
    void findByPath() {
        Repo repo = new Repo();
        repo.setPath(TO_FIND);
        repoRepository.save(repo);
        Repo found = repoRepository.findByPath(TO_FIND);
        Assertions.assertEquals(repo.getId(), found.getId());
        Assertions.assertEquals(TO_FIND, found.getPath());
        repoRepository.remove(found);
        Assertions.assertNull(repoRepository.findByPath(TO_FIND));
    }
}
