package com.airgap.airgapagent.watch;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

/**
 * com.airgap.airgapagent.files
 * Created by Jacques Fontignie on 4/16/2020.
 */
public class FolderWatcher {

    private final Path path;
    private final SnapshotRepository repository;
    private final SnapshotDifferentiator differentiator;
    private final SnapshotMaker snapshotMaker;

    public FolderWatcher(Path path, SnapshotRepository repository) {
        this.path = path;
        this.repository = repository;
        this.snapshotMaker = new SnapshotMaker(path);
        this.differentiator = new SnapshotDifferentiator();
    }

    public List<Difference> scan() throws IOException {
        SnapshotNode lastSnapshot = repository.getLastSnapshot(path);
        SnapshotNode newSnapshot = snapshotMaker.visit();
        repository.saveSnapshot(path, newSnapshot);
        return differentiator.getDifferences(lastSnapshot, newSnapshot);
    }


}
