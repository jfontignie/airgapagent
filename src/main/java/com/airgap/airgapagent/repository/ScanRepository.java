package com.airgap.airgapagent.repository;

import com.airgap.airgapagent.domain.Repo;
import com.airgap.airgapagent.domain.Scan;
import com.airgap.airgapagent.domain.VisitState;
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
public class ScanRepository {

    private final JdbcTemplate jdbcTemplate;

    public ScanRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(Scan scan) {
        if (scan.getId() == null) {

            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(con -> {
                PreparedStatement ps = con.prepareStatement("INSERT INTO scan (id_repo) values (?) ");
                ps.setLong(1, scan.getRepoId());
                return ps;
            }, keyHolder);
            scan.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        } else {
            jdbcTemplate.update("UPDATE scan set id_repo=? where id_scan=?",
                    scan.getRepoId(),
                    scan.getId()
            );
        }
    }


    public void remove(Scan scan) {
        Objects.requireNonNull(scan.getId(), "Scan has been detached");
        jdbcTemplate.update("delete from scan where id_scan=?", scan.getId());
        scan.setId(null);
    }

    public Scan findByRepoIdAndState(Repo repo, VisitState state) {
        List<Scan> scans = jdbcTemplate.query(
                "select s.* from scan s, Visit v where " +
                        "id_repo=? and v.id_scan=s.id_scan and v.st_visit=? ",
                new ScanRowMapper(),
                repo.getId(),
                state);
        return scans.isEmpty() ? null : scans.get(0);
    }

    private static class ScanRowMapper implements RowMapper<Scan> {
        @Override
        public Scan mapRow(ResultSet rs, int rowNum) throws SQLException {
            Scan scan = new Scan();
            scan.setId(rs.getLong("id_scan"));
            scan.setRepoId(rs.getLong("id_repo"));
            return scan;
        }
    }
}
