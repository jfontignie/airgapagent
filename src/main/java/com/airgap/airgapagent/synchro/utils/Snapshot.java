package com.airgap.airgapagent.synchro.utils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * com.airgap.airgapagent.synchro
 * Created by Jacques Fontignie on 4/26/2020.
 */
public class Snapshot {


    private final Map<File, SnapshotStatus> map = new HashMap<>();

    public void put(File file, SnapshotStatus status) {
        map.put(file, status);
    }

    public SnapshotStatus get(File file) {
        return map.get(file);
    }

    public boolean contains(File file) {
        return map.containsKey(file);
    }

}