package com.airgap.airgapagent.files;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;

import java.util.*;
import java.util.stream.Collectors;

/**
 * com.airgap.airgapagent.files
 * Created by Jacques Fontignie on 4/15/2020.
 */
public class SnapshotDifferentiator {

    private static final Logger logger = LoggerFactory.getLogger(SnapshotDifferentiator.class);

    public @NonNull
    List<Difference> getDifferences(@NonNull SnapshotNode first, @NonNull SnapshotNode second) {

        if (!first.getData().equals(second.getData())) {
            logger.debug("Data mismatch between {} and {}", first.getData(), second.getData());
            return Collections.singletonList(new Difference(DifferenceType.DIFFERENT, first, second));
        }
        List<Difference> differences = new ArrayList<>();
        Set<SnapshotNode> allSeconds = new HashSet<>(second.getChildren());
        for (SnapshotNode f : first.getChildren()) {
            boolean found = false;
            for (SnapshotNode s : allSeconds) {
                if (f.getData().getIdentifier().equals(s.getData().getIdentifier())) {
                    found = true;
                    differences.addAll(getDifferences(f, s));
                    if (!allSeconds.remove(s)) {
                        throw new IllegalStateException("The data must be present");
                    }
                    break;
                }
            }
            if (!found) {
                logger.debug("Not found in second snapshot: {}", f.getData());
                differences.add(new Difference(DifferenceType.MISSING_SECOND, f, null));
            }
        }
        differences.addAll(
                allSeconds.stream()
                        .peek(s -> logger.debug("Not found in first snapshot: {}", s.getData()))
                        .map(s -> new Difference(DifferenceType.MISSING_FIRST, null, s))
                        .collect(Collectors.toList()));
        return differences;
    }

    public enum DifferenceType {
        MISSING_FIRST, MISSING_SECOND, DIFFERENT
    }

    public static class Difference {
        private final SnapshotNode first;
        private final SnapshotNode second;
        private final DifferenceType type;
        private SnapshotNode reference;

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
}
