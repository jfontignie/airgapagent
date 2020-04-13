package com.airgap.airgapagent.files;

import com.airgap.airgapagent.flows.work.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * com.airgap.airgapagent.files
 * Created by Jacques Fontignie on 4/12/2020.
 */
public class FileCopyTask implements Work {


    private static final Logger logger = LoggerFactory.getLogger(FileCopyTask.class);

    private final Path source;
    private final List<Target> targets;
    private final List<Path> filesToCopy;


    FileCopyTask(Path source, List<Target> targets, List<Path> filesToCopy) {
        this.source = source;
        this.targets = targets;
        this.filesToCopy = filesToCopy;
    }

    @Override
    public WorkReport call(WorkContext workContext) {
        for (Path file : filesToCopy) {
            Path absolute = Paths.get(source.toString(), file.toString());
            if (Files.notExists(absolute)) {
                logger.debug("File {} does not exist", absolute);
            }
            for (Target target : targets) {
                try {
                    target.copy(absolute, file);
                } catch (IOException e) {
                    logger.error("Impossible to copy file {} to  {}", absolute.toString(), file);
                    return new DefaultWorkReport(WorkStatus.FAILED, workContext, e);
                }
            }
        }
        return new DefaultWorkReport(WorkStatus.COMPLETED, workContext);
    }


    public static class Builder {
        private final List<Target> targets = new ArrayList<>();
        private final List<Path> filesToCopy = new ArrayList<>();
        private Path source;

        public static FileCopyTask.Builder aNewFileCopyTask() {
            return new FileCopyTask.Builder();
        }

        public Builder setSource(Path source) {
            this.source = source;
            return this;
        }

        public Builder addTarget(Path target) {
            return addTarget(target, true);
        }


        public Builder addTarget(Path target, boolean overwrite) {
            this.targets.add(new Target(target, overwrite));
            return this;
        }

        public Builder addFileToCopy(Path fileToCopy) {
            this.filesToCopy.add(fileToCopy);
            return this;
        }

        public FileCopyTask build() {
            return new FileCopyTask(source, targets, filesToCopy);
        }

    }

    public static class Target {
        private final Path target;
        private boolean overwrite;

        public Target(Path target, boolean overwrite) {
            this.target = target;
            this.overwrite = overwrite;
        }

        public void copy(Path source, Path file) throws IOException {
            Path targetFile = Paths.get(target.toString(), file.toString());
            if (overwrite && Files.exists(targetFile)) {
                targetFile = FileUtils.buildNewUniquePath(targetFile);
            }
            atomicCopy(source, targetFile);
        }

        private void atomicCopy(Path source, Path targetFile) throws IOException {
            Files.copy(source, targetFile);
            if (Files.isDirectory(source)) {
                Stream<Path> f = Files.list(source);
                for (Path p : f.collect(Collectors.toList())) {
                    atomicCopy(p, Paths.get(targetFile.toString(), p.getFileName().toString()));
                }
            }
        }
    }
}
