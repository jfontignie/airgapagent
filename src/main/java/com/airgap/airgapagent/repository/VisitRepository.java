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

    public int count() {
        Integer count = jdbcTemplate.queryForObject("SELECT  count(id_visit) from visit", Integer.class);
        return count == null ? 0 : count;
    }

    public Visit findByIdOldestAndStateIsTodo() {
        return jdbcTemplate.queryForObject("SELECT * from visit where id_visit=(select min(id_visit) from visit where st_visit='TODO') ",
                new VisitRowMapper());
    }

    public void remove(Visit visit) {
        jdbcTemplate.update("delete from visit where id_visit=?", visit.getId());
    }

    public static class VisitRowMapper implements RowMapper<Visit> {
        @Override
        public Visit mapRow(ResultSet rs, int rowNum) throws SQLException {
            Visit visit = new Visit();
            visit.setId(rs.getInt("id_visit"));
            visit.setPath(Path.of(rs.getString("de_path")));
            visit.setScanId(rs.getLong("id_scan"));
            visit.setState(VisitState.valueOf(rs.getString("st_visit")));
            return visit;
        }
    }
}
