package com.airgap.airgapagent.files;

import com.airgap.airgapagent.watch.Difference;
import com.airgap.airgapagent.watch.Metadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

/**
 * com.airgap.airgapagent.files
 * Created by Jacques Fontignie on 4/16/2020.
 */
public class FolderSynchronizer implements Synchronizer {

    private static final Logger logger = LoggerFactory.getLogger(FolderSynchronizer.class);
    private final Path target;

    public FolderSynchronizer(Path target) {
        this.target = target;
    }

    @Override
    public void synchronize(List<Difference> differences) throws IOException {
        for (Difference difference : differences) {
            switch (difference.getType()) {
                case MISSING_FIRST:
                    //File added
                    copyDifference(difference.getReference().getData());
                    break;
                case DIFFERENT:
                    //Modified
                    mergeDifference(difference.getFirst().getData(), difference.getSecond().getData());
                    break;
                case MISSING_SECOND:
                    //Deleted
                    deleteDifference(difference.getReference().getData());
                    break;
            }
        }
    }

    void copyDifference(Metadata data) throws IOException {

        Path path = Paths.get(data.getRoot(), data.getRelative());
        Path sourceRoot = Path.of(data.getRoot());

        if (Files.isRegularFile(path)) {
            Files.copy(path, target);
            return;
        }
        try (Stream<Path> stream = Files.walk(path)) {
            stream.sorted(Comparator.naturalOrder())
                    .map(Path::toFile)
                    .forEach(p ->
                            {
                                try {
                                    Files.copy(path, Paths.get(target.toString(), sourceRoot.relativize(path).toString()));
                                } catch (IOException e) {
                                    logger.error("Impossible to copy {}", path, e);
                                }
                            }
                    );
        }

    }

    private void mergeDifference(Metadata first, Metadata second) {

    }

    void deleteDifference(Metadata relative) throws IOException {
        Path path = Paths.get(target.toString(), relative.getRelative());
        if (Files.isRegularFile(path)) {
            Files.delete(path);
            return;
        }
        try (Stream<Path> stream = Files.walk(path)) {
            stream.sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(f -> logger.info("Should delete {}", f));
        }
    }


}
