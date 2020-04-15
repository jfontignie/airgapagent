package com.airgap.airgapagent.files;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.StringJoiner;

/**
 * com.airgap.airgapagent.files
 * Created by Jacques Fontignie on 4/15/2020.
 */
public class SnapshotNode<T> implements Serializable {
    private final Set<SnapshotNode<T>> children = new HashSet<>();
    private final T data;
    private SnapshotNode<T> parent;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public SnapshotNode(
            @JsonProperty("data") T data,
            @JsonProperty("children") Set<SnapshotNode<T>> children) {
        this.data = data;
        this.children.addAll(children);
    }

    public SnapshotNode(T data) {
        this(data, (SnapshotNode<T>) null);
    }

    public SnapshotNode(T data, SnapshotNode<T> parent) {
        this.data = data;
        this.setParent(parent);
    }

    public void setParent(SnapshotNode<T> parent) {
        if (this.parent != parent) {
            this.parent = parent;
            if (parent != null) {
                parent.addChild(this);
            }
        }
    }

    public void addChild(SnapshotNode<T> snapshotNode) {
        if (!children.contains(snapshotNode)) {
            children.add(snapshotNode);
            snapshotNode.setParent(this);
        }
    }

    public T getData() {
        return data;
    }

    public Set<SnapshotNode<T>> getChildren() {
        return Collections.unmodifiableSet(children);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", SnapshotNode.class.getSimpleName() + "[", "]")
                .add("data=" + data)
                .add("children=" + children)
                .toString();
    }
}
