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
    private String relative;


    private String root;

    Metadata(Type type) {
        this.type = type;
    }

    Metadata(String root, Type type, String relative) {
        this.root = root;
        this.type = type;
        this.relative = relative;
    }

    Metadata(Path root, Path path) {
        type = Files.isDirectory(path) ? Type.DIRECTORY : Type.FILE;
        this.relative = root.relativize(path).toString();
        this.root = root.toAbsolutePath().toString();
    }

    public String getRoot() {
        return root;
    }

    public String getRelative() {
        return relative;
    }

    public Type getType() {
        return type;
    }


    public void setRelative(String relative) {
        this.relative = relative;
    }

    public void setRoot(String root) {
        this.root = root;
    }

    @JsonIgnore
    public String getIdentifier() {
        return this.relative;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Metadata.class.getSimpleName() + "[", "]")
                .add("root='" + root + "'")
                .add("relative='" + relative + "'")
                .add("type=" + type)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Metadata metadata = (Metadata) o;
        return Objects.equals(getRoot(), metadata.getRoot()) &&
                getType() == metadata.getType() &&
                Objects.equals(getRelative(), metadata.getRelative());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRelative(), getRoot(), getType());
    }


}
