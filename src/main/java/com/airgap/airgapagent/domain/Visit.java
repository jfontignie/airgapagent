package com.airgap.airgapagent.domain;

import java.nio.file.Path;
import java.time.Instant;

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

    private void update() {
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
        update();
    }

    public Instant getUpdated() {
        return updated;
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Long getScanId() {
        return scanId;
    }

    public void setScanId(Long scanId) {
        this.scanId = scanId;
    }
}
