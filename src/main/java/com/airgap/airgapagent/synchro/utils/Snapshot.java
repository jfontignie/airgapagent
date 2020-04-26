package com.airgap.airgapagent.synchro.utils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * com.airgap.airgapagent.synchro
 * Created by Jacques Fontignie on 4/26/2020.
 */
public class Snapshot {


    private final File file;
    private final Map<File, SnapshotStatus> map = new HashMap<>();

    public Snapshot(File file) {
        this.file = file;
    }

    public void init() throws IOException {


    }

    public void put(File file, SnapshotStatus status) {
        map.put(file, status);
    }

    public SnapshotStatus get(File file) {
        return map.get(file);
    }

    public boolean contains(File file) {
        return map.containsKey(file);
    }

    public void close() {
    }
}
