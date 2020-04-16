package com.airgap.airgapagent.files;

import com.airgap.airgapagent.watch.Difference;
import com.airgap.airgapagent.watch.SnapshotRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

/**
 * com.airgap.airgapagent.files
 * Created by Jacques Fontignie on 4/16/2020.
 */
public class FolderSynchronizer implements Synchronizer {

    private static final Logger logger = LoggerFactory.getLogger(FolderSynchronizer.class);
    private final SnapshotRepository repository;
    private final Path target;

    public FolderSynchronizer(SnapshotRepository repository, Path target) {
        this.repository = repository;
        this.target = target;
    }

    @Override
    public void synchronize(List<Difference> differences) throws IOException {
        for (Difference difference : differences) {
            switch (difference.getType()) {
                case MISSING_FIRST:
                    //File added
                    copyDifference(difference);
                    break;
                case DIFFERENT:
                    //Modified
                    mergeDifference(difference);
                    break;
                case MISSING_SECOND:
                    //Deleted
                    deleteDifference(difference);
                    break;
            }
        }
    }

    private void deleteDifference(Difference difference) throws IOException {
        Path path = Paths.get(target.toString(), difference.getReference().getData().getRelative());
        try (Stream<Path> stream = Files.walk(path)) {
            stream.map(Path::toFile)
                    .forEach(f -> logger.info("Should delete {}", f));
        }
    }

    private void mergeDifference(Difference difference) {

    }

    private void copyDifference(Difference difference) {

    }

}
