package com.airgap.airgapagent.files;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

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

    private SnapshotNode visit(Path root) throws IOException {
        logger.trace("Visiting {}", root.getFileName());
        if (Files.isDirectory(root)) {
            return visitFolder(root);
        } else {
            return visitFile(root);
        }
    }

    private SnapshotNode visitFile(Path root) throws IOException {
        logger.trace("Visiting File {}", root.getFileName());
        return new SnapshotNode(MetadaFactory.analyze(root));
    }

    private SnapshotNode visitFolder(Path root) throws IOException {
        logger.trace("Visiting Folder {}", root.getFileName());

        SnapshotNode node = new SnapshotNode(MetadaFactory.analyze(root));
        List<Path> children = Files.list(root).collect(Collectors.toList());
        for (Path p : children) {
            node.addChild(visit(p));
        }
        return node;
    }
}
