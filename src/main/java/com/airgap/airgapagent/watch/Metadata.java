package com.airgap.airgapagent.watch;

import com.airgap.airgapagent.files.Type;
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
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = FolderMetadata.class, name = "DIRECTORY"),
        @JsonSubTypes.Type(value = FileMetadata.class, name = "FILE")
})
public abstract class Metadata implements Serializable {

    private final Type type;
    private final String relative;

    Metadata(Type type, String relative) {
        this.type = type;
        this.relative = relative;
    }

    Metadata(Path root, Path path) {
        type = Files.isDirectory(path) ? Type.DIRECTORY : Type.FILE;
        this.relative = root.relativize(path).toString();
    }

    public String getRelative() {
        return relative;
    }

    public Type getType() {
        return type;
    }

    @JsonIgnore
    public String getIdentifier() {
        return this.relative;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Metadata.class.getSimpleName() + "[", "]")
                .add("relative='" + relative + "'")
                .add("type=" + type)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Metadata metadata = (Metadata) o;
        return getType() == metadata.getType() &&
                Objects.equals(getRelative(), metadata.getRelative());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRelative(), getType());
    }
}
