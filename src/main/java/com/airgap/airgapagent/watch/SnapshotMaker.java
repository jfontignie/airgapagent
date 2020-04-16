package com.airgap.airgapagent.watch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * com.airgap.airgapagent.files
 * Created by Jacques Fontignie on 4/15/2020.
 */
public class SnapshotMaker {

    private static final Logger logger = LoggerFactory.getLogger(SnapshotMaker.class);

    private final Path root;

    public SnapshotMaker(Path root) {
        this.root = root;
    }

    public SnapshotNode visit() throws IOException {

        return visit(root);
    }

    private SnapshotNode visit(Path node) throws IOException {
        logger.trace("Visiting {}", node.getFileName());
        if (Files.isDirectory(node)) {
            return visitFolder(node);
        } else {
            return visitFile(node);
        }
    }

    private SnapshotNode visitFile(Path node) throws IOException {
        logger.trace("Visiting File {}", node.getFileName());
        return new SnapshotNode(MetadataFactory.analyze(root, node));
    }

    private SnapshotNode visitFolder(Path folder) throws IOException {
        logger.trace("Visiting Folder {}", folder.getFileName());

        SnapshotNode node = new SnapshotNode(MetadataFactory.analyze(root, folder));
        List<Path> children;
        try (Stream<Path> list = Files.list(folder)) {
            children = list.collect(Collectors.toList());
        }
        for (Path p : children) {
            node.addChild(visit(p));
        }
        return node;
    }
}
