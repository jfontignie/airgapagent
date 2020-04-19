package com.airgap.airgapagent.watch;

import com.airgap.airgapagent.files.Type;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * com.airgap.airgapagent.files
 * Created by Jacques Fontignie on 4/15/2020.
 */
public class FileMetadata extends Metadata {
    private String contentType;
    private final long size;
    private final long fileTime;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    FileMetadata(@JsonProperty("root") String root,
                 @JsonProperty("fileTime") long fileTime,
                 @JsonProperty("type") Type type,
                 @JsonProperty("relative") String relative,
                 @JsonProperty("contentType") String contentType,
                 @JsonProperty("size") long size) {
        super(root, type, relative);
        this.fileTime = fileTime;
        this.contentType = contentType;
        this.size = size;
    }

    public FileMetadata(Path root, Path path) throws IOException {
        super(root, path);
        this.fileTime = Files.getLastModifiedTime(path).toMillis();
        try {
            contentType = Files.probeContentType(path);
        } catch (IOException e) {
            contentType = null;
        }
        size = Files.size(path);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", FileMetadata.class.getSimpleName() + "[", "]")
                .add("super='" + super.toString() + "'")
                .add("fileTime=" + fileTime)
                .add("contentType='" + contentType + "'")
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FileMetadata)) return false;
        if (!super.equals(o)) return false;
        FileMetadata metadata = (FileMetadata) o;
        return getFileTime() == metadata.getFileTime() &&
                size == metadata.size &&
                Objects.equals(contentType, metadata.contentType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getFileTime(), contentType, size);
    }

    public long getFileTime() {
        return fileTime;
    }
}
