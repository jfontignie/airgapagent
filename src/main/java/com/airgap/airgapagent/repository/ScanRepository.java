package com.airgap.airgapagent.repository;

import com.airgap.airgapagent.domain.Repo;
import com.airgap.airgapagent.domain.Scan;
import com.airgap.airgapagent.domain.ScanState;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

/**
 * com.airgap.airgapagent.batch
 * Created by Jacques Fontignie on 5/5/2020.
 */
@Service
public class ScanRepository {

    private final JdbcTemplate jdbcTemplate;

    public ScanRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(Scan scan) {
        if (scan.getId() == null) {

            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(con -> {
                PreparedStatement ps = con.prepareStatement("INSERT INTO scan (id_repo,st_scan,sq_sequence) values (?,?,?) ");
                ps.setLong(1, scan.getRepoId());
                ps.setString(2, scan.getState().toString());
                ps.setInt(3, scan.getSequence());
                return ps;
            }, keyHolder);
            scan.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        } else {
            jdbcTemplate.update("UPDATE scan set id_repo=?, st_scan=?, sq_sequence=? where id_scan=?",
                    scan.getRepoId(),
                    scan.getState(),
                    scan.getSequence(),
                    scan.getId()
            );
        }
    }


    public void remove(Scan scan) {
        jdbcTemplate.update("delete from scan where id_scan=?", scan.getId());
    }

    public Scan findByRepoIdAndState(Repo repo, ScanState state) {
        return jdbcTemplate.queryForObject("select * from scan where id_repo=? and st_scan=?",
                new ScanRowMapper(),
                repo.getId(),
                state);
    }

    private static class ScanRowMapper implements RowMapper<Scan> {
        @Override
        public Scan mapRow(ResultSet rs, int rowNum) throws SQLException {
            Scan scan = new Scan();
            scan.setId(rs.getLong("id_scan"));
            scan.setSequence(rs.getInt("sq_sequence"));
            scan.setId(rs.getLong("id_repo"));
            scan.setState(ScanState.valueOf(rs.getString("st_scan")));
            return scan;
        }
    }
}
