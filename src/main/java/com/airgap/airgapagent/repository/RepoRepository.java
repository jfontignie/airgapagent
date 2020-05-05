package com.airgap.airgapagent.repository;

import com.airgap.airgapagent.domain.Repo;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

/**
 * com.airgap.airgapagent.batch
 * Created by Jacques Fontignie on 5/5/2020.
 */
@Service
public class RepoRepository {

    private final JdbcTemplate jdbcTemplate;

    public RepoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(Repo repo) {
        if (repo.getId() == null) {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(con -> {
                PreparedStatement ps = con.prepareStatement("INSERT INTO repo (de_root) VALUES ( ? )");
                ps.setString(1, repo.getPath());
                return ps;
            }, keyHolder);
            repo.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        } else {
            jdbcTemplate.update("UPDATE repo set de_root=? where id_repo=?",
                    repo.getPath(),
                    repo.getId()
            );
        }
    }

    public Repo findByPath(String path) {
        List<Repo> list = jdbcTemplate.query("SELECT * from repo where de_root=?", new RepoRowMapper(), path);
        if (list.isEmpty()) return null;
        return list.get(0);
    }

    public void remove(Repo repo) {
        jdbcTemplate.update("delete from repo where id_repo=?", repo.getId());
    }

    private static class RepoRowMapper implements RowMapper<Repo> {
        @Override
        public Repo mapRow(ResultSet rs, int rowNum) throws SQLException {
            Repo repo = new Repo();
            repo.setId(rs.getLong("id_repo"));
            repo.setPath(rs.getString("de_root"));
            return repo;
        }
    }
}
