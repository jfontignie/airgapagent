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
public class FolderSynchronizer {

    private static final Logger logger = LoggerFactory.getLogger(FolderSynchronizer.class);

    private final Path second;
    private final Path first;

    public FolderSynchronizer(Path first, Path second) {
        this.first = first;
        this.second = second;
    }

    public void synchronize() throws IOException {
        logger.info("Starting synchronization between {} and {}", first, second);
        synchronize(first, second);
        logger.info("Finished synchronization between {} and {}", first, second);
    }

    private void synchronize(Path first, Path second) throws IOException {
        logger.debug("Synchronizing between {} and {}", first, second);
        if (Files.notExists(second)) {
            if (Files.isRegularFile(first)) {
                Files.copy(first, second);
            } else {
                Files.createDirectory(second);
            }
        }

        if (Files.isDirectory(first)) {
            List<Path> paths = Files.list(first).collect(Collectors.toList());
            for (Path f : paths) {
                synchronize(f, Path.of(second.toString(), String.valueOf(f.getFileName())));
            }
        }
    }

    private void copyFile(Path first, Path second) throws IOException {
        Files.copy(first, second);
    }

}