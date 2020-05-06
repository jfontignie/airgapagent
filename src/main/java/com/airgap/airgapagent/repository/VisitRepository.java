package com.airgap.airgapagent.repository;

import com.airgap.airgapagent.domain.Visit;
import com.airgap.airgapagent.domain.VisitState;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

/**
 * com.airgap.airgapagent.batch
 * Created by Jacques Fontignie on 5/5/2020.
 */
@Service
public class VisitRepository {

    private final JdbcTemplate jdbcTemplate;


    public VisitRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public void save(Visit visit) {
        visit.update(); //Set the new timestamp
        if (visit.getId() == null) {

            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(con -> {
                PreparedStatement ps = con.prepareStatement("INSERT INTO visit (de_path, st_visit,dt_update,id_scan) " +
                        "VALUES ( ?, ?, ? , ? )");
                ps.setString(1, visit.getPath());
                ps.setString(2, visit.getState().toString());
                ps.setTimestamp(3, Timestamp.from(visit.getUpdated()));
                ps.setLong(4, visit.getScanId());
                return ps;
            }, keyHolder);
            visit.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());

        } else {
            jdbcTemplate.update("UPDATE visit set de_path=? , st_visit=?, dt_update=?, id_scan=? " +
                            "where id_visit=?",
                    visit.getPath(),
                    visit.getState(),
                    visit.getUpdated(),
                    visit.getScanId(),
                    visit.getId()
            );

        }

    }


    public Visit findByScanIdAndStateAndIdOldest(Long id, VisitState state) {
        List<Visit> list = jdbcTemplate.query(
                "SELECT * from visit where " +
                        "id_visit=(select min(id_visit) from visit where st_visit=? and id_scan=?) ",
                new VisitRowMapper(),
                state, id);
        return list.isEmpty() ? null : list.get(0);
    }

    public void remove(Visit visit) {
        jdbcTemplate.update("delete from visit where id_visit=?", visit.getId());
        visit.setId(null);
    }


    public List<Visit> findByPathAndNoScanIdOrderByDateDesc(Long scanId, String folder) {

        return jdbcTemplate.query("Select * from Visit where de_path=? and NOT id_scan=? order by dt_update Desc",
                new VisitRowMapper(),
                folder, scanId);
    }

    public static class VisitRowMapper implements RowMapper<Visit> {
        @Override
        public Visit mapRow(ResultSet rs, int rowNum) throws SQLException {
            Visit visit = new Visit();
            visit.setId(rs.getLong("id_visit"));
            visit.setPath(Path.of(rs.getString("de_path")));
            visit.setScanId(rs.getLong("id_scan"));
            visit.setState(VisitState.valueOf(rs.getString("st_visit")));
            visit.setUpdated(rs.getTimestamp("dt_update").toInstant());
            return visit;
        }
    }
}
