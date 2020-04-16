package com.airgap.airgapagent.watch;

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
public class SnapshotNode implements Serializable {
    private final Set<SnapshotNode> children = new HashSet<>();
    private final Metadata data;
    private SnapshotNode parent;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public SnapshotNode(
            @JsonProperty("data") Metadata data,
            @JsonProperty("children") Set<SnapshotNode> children) {
        this.data = data;
        this.children.addAll(children);
    }

    public SnapshotNode(Metadata data) {
        this(data, (SnapshotNode) null);
    }

    public SnapshotNode(Metadata data, SnapshotNode parent) {
        this.data = data;
        this.setParent(parent);
    }

    public void setParent(SnapshotNode parent) {
        if (this.parent != parent) {
            this.parent = parent;
            if (parent != null) {
                parent.addChild(this);
            }
        }
    }

    public void addChild(SnapshotNode snapshotNode) {
        if (!children.contains(snapshotNode)) {
            children.add(snapshotNode);
            snapshotNode.setParent(this);
        }
    }

    public Metadata getData() {
        return data;
    }

    public Set<SnapshotNode> getChildren() {
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
