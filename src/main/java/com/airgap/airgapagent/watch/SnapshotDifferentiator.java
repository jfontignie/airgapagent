package com.airgap.airgapagent.watch;

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
    List<Difference> getDifferences(SnapshotNode first, SnapshotNode second) {
        if (first == null && second == null) {
            return Collections.emptyList();
        }
        if (first == null) {
            return Collections.singletonList(new Difference(DifferenceType.MISSING_SECOND, second));
        }
        if (second == null) {
            return Collections.singletonList(new Difference(DifferenceType.MISSING_FIRST, first));
        }
        return calculateDifferences(first, second);
    }

    private List<Difference> calculateDifferences(SnapshotNode first, SnapshotNode second) {

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
        allSeconds.forEach(s -> logger.debug("Not found in first snapshot: {}", s.getData()));
        differences.addAll(
                allSeconds.stream()
                        .map(s -> new Difference(DifferenceType.MISSING_FIRST, null, s))
                        .collect(Collectors.toList()));
        return differences;
    }


}
