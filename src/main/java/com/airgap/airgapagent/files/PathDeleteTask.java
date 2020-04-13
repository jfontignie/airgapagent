package com.airgap.airgapagent.files;

import com.airgap.airgapagent.flows.work.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * com.airgap.airgapagent.files
 * Created by Jacques Fontignie on 4/12/2020.
 */
public class PathDeleteTask implements Work {

    private final List<Path> toDelete;
    private static final Logger logger = LoggerFactory.getLogger(PathDeleteTask.class);

    PathDeleteTask(List<Path> toDelete) {
        this.toDelete = toDelete;
    }

    @Override
    public WorkReport call(WorkContext workContext) {
        for (Path path : toDelete) {
            try {
                logger.info("Deleting files in folder {}",path);
                Files.list(path);
                //Files.delete(path);
            } catch (IOException e) {
                return new DefaultWorkReport(WorkStatus.FAILED, workContext, e);
            }
        }
        return new DefaultWorkReport(WorkStatus.COMPLETED,workContext);
    }

    public static class Builder {
        private final List<Path> toDelete = new ArrayList<>();

        public static Builder aNewPathDeleteTask() {
            return new Builder();
        }

        public Builder addFolder(Path path) {
            toDelete.add(path);
            return this;
        }

        public PathDeleteTask build() {
            return new PathDeleteTask(toDelete);
        }
    }
}
