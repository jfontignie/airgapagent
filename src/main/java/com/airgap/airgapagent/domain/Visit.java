package com.airgap.airgapagent.domain;

import java.nio.file.Path;
import java.time.Instant;
import java.util.StringJoiner;

/**
 * com.airgap.airgapagent.batch
 * Created by Jacques Fontignie on 5/5/2020.
 */
public class Visit {

    private Long id = null;

    private Long scanId;
    private String path;

    private VisitState state;
    private Instant updated;

    public Visit() {
    }

    public Visit(Scan scan, Path path) {
        this(scan.getId(), path);
    }

    public Visit(Long scanId, Path path) {
        this.scanId = scanId;
        setPath(path);
        this.state = VisitState.TODO;
        update();
    }

    private String sanitize(Path root) {
        return root.toString().toLowerCase();
    }

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

    public void setUpdated(Instant updated) {
        this.updated = updated;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getScanId() {
        return scanId;
    }

    public void setScanId(Long scanId) {
        this.scanId = scanId;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Visit.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("scanId=" + scanId)
                .add("path='" + path + "'")
                .add("state=" + state)
                .add("updated=" + updated)
                .toString();
    }
}
