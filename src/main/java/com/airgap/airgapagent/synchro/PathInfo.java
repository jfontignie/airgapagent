package com.airgap.airgapagent.synchro;

import java.nio.file.Path;

/**
 * com.airgap.airgapagent.synchro
 * Created by Jacques Fontignie on 4/18/2020.
 */
public class PathInfo {
    private final Path baseFolder;
    private final Path target;

    public PathInfo(Path baseFolder, Path target) {
        this.baseFolder = baseFolder;
        this.target = target;
    }

    public PathInfo(String baseFolder, String target) {
        this(Path.of(baseFolder), Path.of(target));
    }

    public Path getBaseFolder() {
        return baseFolder;
    }

    public Path getOriginalPath() {
        return target;
    }

    public Path getRelative() {
        return baseFolder.relativize(target);
    }
}
