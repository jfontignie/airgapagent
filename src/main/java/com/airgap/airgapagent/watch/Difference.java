package com.airgap.airgapagent.watch;

import java.util.StringJoiner;

/**
 * com.airgap.airgapagent.files
 * Created by Jacques Fontignie on 4/16/2020.
 */
public class Difference {
    private final DifferenceType type;
    private SnapshotNode first;
    private SnapshotNode second;
    private final SnapshotNode reference;

    public Difference(DifferenceType type, SnapshotNode first, SnapshotNode second) {
        this.first = first;
        this.second = second;
        this.type = type;
        switch (type) {
            case DIFFERENT:
            case MISSING_SECOND:
                reference = first;
                break;
            case MISSING_FIRST:
                reference = second;
                break;
            default:
                throw new IllegalStateException("Invalid type");
        }
    }

    public Difference(DifferenceType type, SnapshotNode node) {
        this.type = type;
        reference = node;
        switch (type) {
            case DIFFERENT:
                throw new IllegalStateException("Wrong constructor: use both constructor");
            case MISSING_FIRST:
                second = node;
                break;
            case MISSING_SECOND:
                first = node;
                break;
            default:
                throw new IllegalStateException("Invalid type");
        }
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Difference.class.getSimpleName() + "[", "]")
                .add("first=" + first)
                .add("second=" + second)
                .add("type=" + type)
                .toString();
    }

    public DifferenceType getType() {
        return type;
    }

    public SnapshotNode getReference() {
        return reference;
    }
}
