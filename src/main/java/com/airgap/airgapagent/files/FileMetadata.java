package com.airgap.airgapagent.files;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private static final Logger logger = LoggerFactory.getLogger(FileMetadata.class);
    private final String contentType;
    private final long size;
    private final long fileTime;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    FileMetadata(@JsonProperty("fileTime") long fileTime,
                 @JsonProperty("path") Path path,
                 @JsonProperty("type") Type type,
                 @JsonProperty("fileName") String fileName,
                 @JsonProperty("contentType") String contentType,
                 @JsonProperty("size") long size) {
        super(path, type, fileName);
        this.fileTime = fileTime;
        this.contentType = contentType;
        this.size = size;
    }

    public FileMetadata(Path path) throws IOException {
        super(path);
        this.fileTime = Files.getLastModifiedTime(path).toMillis();
        contentType = Files.probeContentType(path);
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
