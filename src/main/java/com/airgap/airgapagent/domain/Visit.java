package com.airgap.airgapagent.domain;

import javax.persistence.*;
import java.nio.file.Path;
import java.time.Instant;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * com.airgap.airgapagent.batch
 * Created by Jacques Fontignie on 5/5/2020.
 */
@Entity
public class Visit {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id = null;

    @ManyToOne
    private Scan scan;

    private String path;

    private VisitState state;

    private Instant updated;

    public Visit() {
    }

    public Visit(Scan scan, Path path) {
        this.scan = scan;
        setPath(path);
        this.state = VisitState.TODO;
        update();
    }

    private String sanitize(Path root) {
        return root.toString().toLowerCase();
    }

    @PrePersist
    @PreUpdate
    public void update() {
        this.updated = Instant.now();
    }

    public String getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = sanitize(path);
    }

    public VisitState getState() {
        return state;
    }

    public void setState(VisitState state) {
        this.state = state;
    }

    public Instant getUpdated() {
        return updated;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Scan getScan() {
        return scan;
    }

    public void setScan(Scan scan) {
        this.scan = scan;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Visit.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("scanId" + (scan != null ? scan.getId() : null))
                .add("path='" + path + "'")
                .add("state=" + state)
                .add("updated=" + updated)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Visit)) return false;
        Visit visit = (Visit) o;
        return Objects.equals(id, visit.id) &&
                Objects.equals(scan, visit.scan) &&
                Objects.equals(path, visit.path) &&
                state == visit.state;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, scan, path, state);
    }

    public void setUpdated(Instant instant) {
        this.updated = instant;
    }
}
