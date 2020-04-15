package com.airgap.airgapagent.files;

import java.util.HashSet;
import java.util.Set;

/**
 * com.airgap.airgapagent.files
 * Created by Jacques Fontignie on 4/15/2020.
 */
public class SnapshotNode<T> {
    private final Set<SnapshotNode> children = new HashSet<>();
    private final T data;
    private SnapshotNode parent;

    public SnapshotNode(T data) {
        this(data, null);
    }


    public SnapshotNode(T data, SnapshotNode parent) {
        this.data = data;
        this.setParent(parent);
        this.parent.addChild(this);
    }

    public void setParent(SnapshotNode parent) {
        if (this.parent != parent) {
            this.parent = parent;
            parent.addChild(this);
        }
    }

    public void addChild(SnapshotNode snapshotNode) {
        if (children.contains(snapshotNode)) {
            children.add(snapshotNode);
            snapshotNode.setParent(this);
        }
    }

    public T getData() {
        return data;
    }
}
