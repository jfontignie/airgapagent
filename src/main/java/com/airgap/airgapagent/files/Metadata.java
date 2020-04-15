package com.airgap.airgapagent.files;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;

/**
 * com.airgap.airgapagent.files
 * Created by Jacques Fontignie on 4/15/2020.
 */
public class Metadata {

    private final FileTime fileTime;
    private final Path path;
    private final Type type;

    public Metadata(Path path) throws IOException {
        this.fileTime = Files.getLastModifiedTime(path);
        this.path = path;
        type = Files.isDirectory(path) ? Type.DIRECTORY : Type.FILE;
    }

    public FileTime getFileTime() {
        return fileTime;
    }

    public Path getPath() {
        return path;
    }

    public Type getType() {
        return type;
    }
}
