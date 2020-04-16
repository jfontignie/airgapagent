package com.airgap.airgapagent.files;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.nio.file.Path;

/**
 * com.airgap.airgapagent.files
 * Created by Jacques Fontignie on 4/16/2020.
 */
public interface SnapshotRepository {
    @Nullable
    SnapshotNode getLastSnapshot(Path path);

    void saveSnapshot(Path path, @NonNull SnapshotNode newSnapshot);

    int count();
}
