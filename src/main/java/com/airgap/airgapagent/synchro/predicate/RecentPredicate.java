package com.airgap.airgapagent.synchro.predicate;

import com.airgap.airgapagent.synchro.utils.PathInfo;
import com.airgap.airgapagent.synchro.utils.Snapshot;
import com.airgap.airgapagent.synchro.utils.SnapshotStatus;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

/**
 * com.airgap.airgapagent.synchro
 * Created by Jacques Fontignie on 4/19/2020.
 */
public class RecentPredicate implements Predicate {

    private Path statusFile;
    private Snapshot snapshot;

    public RecentPredicate() {
        //Nothing to do
    }

    public String getStatusFile() {
        return statusFile.toString();
    }

    public void setStatusFile(String statusFile) {
        this.statusFile = Path.of(statusFile);
    }

    @Override
    public void init() {
        Objects.requireNonNull(statusFile, "Status file not initiated");
        snapshot = new Snapshot(statusFile.toFile());
    }

    @Override
    public boolean call(PathInfo path) throws IOException {
        Objects.requireNonNull(snapshot, "Init method not called");

        Path originalPath = path.getOriginalPath();
        if (Files.isDirectory(originalPath)) {
            return true;
        }
        File file = originalPath.toFile();
        SnapshotStatus status = snapshot.get(file);
        SnapshotStatus newStatus = new SnapshotStatus(Files.getLastModifiedTime(path.getOriginalPath()).toMillis());
        snapshot.put(file, newStatus);
        if (status == null) {
            return true;
        } else {
            long lastScan = status.getLastTime();
            long currentFileTime = Files.getLastModifiedTime(originalPath).toMillis();
            return currentFileTime > lastScan;
        }
    }


    @Override
    public void close() {
        snapshot.close();
    }
}
