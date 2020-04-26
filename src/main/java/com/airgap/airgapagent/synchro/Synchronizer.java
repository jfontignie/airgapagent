package com.airgap.airgapagent.synchro;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * com.airgap.airgapagent.synchro
 * Created by Jacques Fontignie on 4/17/2020.
 */
public class Synchronizer {

    private static final Logger logger = LoggerFactory.getLogger(Synchronizer.class);

    private Path baseFolder;
    private Work flow;

    public Synchronizer(Path baseFolder, Work flow) {
        this.baseFolder = baseFolder;
        this.flow = flow;
    }

    public Synchronizer() {
        //Nothing to do
    }

    public String getBaseFolder() {
        return baseFolder.toAbsolutePath().toString();
    }

    public void setBaseFolder(String baseFolder) {
        this.baseFolder = Path.of(baseFolder);
    }

    public Work getFlow() {
        return flow;
    }

    public void setFlow(Work flow) {
        this.flow = flow;
    }

    public void run() throws IOException {
        flow.init();

        Files.walkFileTree(baseFolder, new FileVisitor<>() {
            @Override
            public FileVisitResult preVisitDirectory(Path path, BasicFileAttributes basicFileAttributes) {
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path path, BasicFileAttributes basicFileAttributes) throws IOException {
                logger.info("Path has been identified to have been modified: {}", path);
                flow.call(new PathInfo(baseFolder, path));
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path path, IOException e) {
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path path, IOException e) {
                return FileVisitResult.CONTINUE;
            }
        });
    }


}
