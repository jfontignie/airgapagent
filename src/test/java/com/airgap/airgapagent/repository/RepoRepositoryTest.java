package com.airgap.airgapagent.repository;

import com.airgap.airgapagent.domain.Repo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.test.DataSourceInitializer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * com.airgap.airgapagent.repository
 * Created by Jacques Fontignie on 5/6/2020.
 */
class RepoRepositoryTest {

    private static final String TO_FIND = "toFind";
    private RepoRepository repoRepository;

    @BeforeEach
    public void setUp() {

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.sqlite.JDBC");
        dataSource.setUrl("jdbc:sqlite:test.sqlite");
        DataSourceInitializer initializer = new DataSourceInitializer();
        initializer.setDataSource(dataSource);
        initializer.setInitScripts(
                new Resource[]{
                        new ClassPathResource("delete-all.sql"),
                        new ClassPathResource("schema-all.sql")
                }
        );
        initializer.afterPropertiesSet();

        repoRepository = new RepoRepository(new JdbcTemplate(dataSource));

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
        repoRepository.remove(found);
        Assertions.assertNull(repoRepository.findByPath(TO_FIND));
    }
}
