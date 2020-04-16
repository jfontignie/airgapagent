package com.airgap.airgapagent.watch;

import java.nio.file.Path;
import java.util.*;

/**
 * com.airgap.airgapagent.files
 * Created by Jacques Fontignie on 4/16/2020.
 */
public class MemorySnapshotRepository implements SnapshotRepository {

    private final Map<Path, List<SnapshotNode>> map = new HashMap<>();

    @Override
    public SnapshotNode getLastSnapshot(Path path) {
        List<SnapshotNode> res = map.get(path);
        if (res == null) {
            return null;
        }
        return res.get(res.size() - 1);
    }

    @Override
    public void saveSnapshot(Path path, SnapshotNode newSnapshot) {
        map.putIfAbsent(path, new ArrayList<>());
        map.get(path).add(newSnapshot);
    }

    @Override
    public int count() {
        return map.values()
                .stream()
                .filter(Objects::nonNull)
                .mapToInt(List::size)
                .reduce(0, Integer::sum);
    }
}
