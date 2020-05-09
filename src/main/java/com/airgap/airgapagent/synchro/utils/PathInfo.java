package com.airgap.airgapagent.synchro.utils;

import java.nio.file.Path;
import java.util.Objects;

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

    public static PathInfo of(String baseFolder, String target) {
        return new PathInfo(baseFolder, target);
    }

    public static PathInfo of(String s) {
        return of(s, s);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PathInfo)) return false;
        PathInfo pathInfo = (PathInfo) o;
        return Objects.equals(baseFolder, pathInfo.baseFolder) &&
                Objects.equals(target, pathInfo.target);
    }

    @Override
    public int hashCode() {
        return Objects.hash(baseFolder, target);
    }
}
