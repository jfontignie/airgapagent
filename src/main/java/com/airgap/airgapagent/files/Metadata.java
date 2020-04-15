package com.airgap.airgapagent.files;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.StringJoiner;

/**
 * com.airgap.airgapagent.files
 * Created by Jacques Fontignie on 4/15/2020.
 */
public abstract class Metadata implements Serializable {

    private final long fileTime;
    private final Path path;
    private final Type type;
    private final String fileName;

    Metadata(long fileTime, Path path, Type type, String fileName) {
        this.fileTime = fileTime;
        this.path = path;
        this.type = type;
        this.fileName = fileName;
    }

    public Metadata(Path path) throws IOException {
        this.fileTime = Files.getLastModifiedTime(path).toMillis();
        this.path = path;
        type = Files.isDirectory(path) ? Type.DIRECTORY : Type.FILE;
        this.fileName = path.getFileName().toString();
    }

    public String getFileName() {
        return fileName;
    }

    public long getFileTime() {
        return fileTime;
    }

    public Path getPath() {
        return path;
    }

    public Type getType() {
        return type;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Metadata.class.getSimpleName() + "[", "]")
                .add("fileTime=" + fileTime)
                .add("path=" + path)
                .add("type=" + type)
                .add("fileName='" + fileName + "'")
                .toString();
    }
}
