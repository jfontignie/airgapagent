package com.airgap.airgapagent.files;

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
public class SimpleFolderSynchronizer {

    private static final Logger logger = LoggerFactory.getLogger(SimpleFolderSynchronizer.class);

    private final Path second;
    private final Path first;

    public SimpleFolderSynchronizer(Path first, Path second) {
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
        if (Files.isDirectory(first)) {
            synchronizeFolder(first, second);
        } else {
            synchronizeFile(first, second);
        }
    }

    private void synchronizeFile(Path first, Path second) throws IOException {
        if (Files.notExists(second)) {
            Files.copy(first, second);
        }
    }

    private void synchronizeFolder(Path first, Path second) throws IOException {
        if (Files.notExists(second)) {
            Files.createDirectory(second);
        }

        List<Path> paths;
        try (Stream<Path> list = Files.list(first)) {
            paths = list.collect(Collectors.toList());
        }
        for (Path f : paths) {
            synchronize(f, Path.of(second.toString(), String.valueOf(f.getFileName())));
        }
    }


}
