package com.airgap.airgapagent.files;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * com.airgap.airgapagent.files
 * Created by Jacques Fontignie on 4/15/2020.
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = FolderMetadata.class, name = "DIRECTORY"),
        @JsonSubTypes.Type(value = FileMetadata.class, name = "FILE")
})
public abstract class Metadata implements Serializable {

    private transient final Path path;
    private final Type type;
    private final String fileName;

    Metadata(Path path, Type type, String fileName) {
        this.path = path;
        this.type = type;
        this.fileName = fileName;
    }

    public Metadata(Path path) {
        this.path = path;
        type = Files.isDirectory(path) ? Type.DIRECTORY : Type.FILE;
        this.fileName = path.getFileName().toString();
    }

    public String getFileName() {
        return fileName;
    }

    public Path getPath() {
        return path;
    }

    public Type getType() {
        return type;
    }

    @JsonIgnore
    public String getIdentifier() {
        return this.fileName;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Metadata.class.getSimpleName() + "[", "]")
                .add("fileName='" + fileName + "'")
                .add("path=" + path)
                .add("type=" + type)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Metadata metadata = (Metadata) o;
        return Objects.equals(getPath(), metadata.getPath()) &&
                getType() == metadata.getType() &&
                Objects.equals(getFileName(), metadata.getFileName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPath(), getType(), getFileName());
    }
}
