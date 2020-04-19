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
    private int earlierThan;
    private Work flow;

    public Synchronizer(Path baseFolder, int earlierThan, Work flow) {
        this.baseFolder = baseFolder;
        this.earlierThan = earlierThan;
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

    public int getEarlierThan() {
        return earlierThan;
    }

    public void setEarlierThan(int earlierThan) {
        this.earlierThan = earlierThan;
    }

    public Work getFlow() {
        return flow;
    }

    public void setFlow(Work flow) {
        this.flow = flow;
    }

    public void run() throws IOException {
        flow.init();

        long lastTime = System.currentTimeMillis() - earlierThan * 1000;
        Files.walkFileTree(baseFolder, new FileVisitor<>() {
            @Override
            public FileVisitResult preVisitDirectory(Path path, BasicFileAttributes basicFileAttributes) throws IOException {
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path path, BasicFileAttributes basicFileAttributes) throws IOException {
//                if (Files.getLastModifiedTime(path).toMillis() < lastTime) {
//                    return FileVisitResult.CONTINUE;
//                }
                logger.info("Path has been identified to have been modified: {}", path);
                flow.call(new PathInfo(baseFolder, path));
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path path, IOException e) throws IOException {
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path path, IOException e) throws IOException {
                return FileVisitResult.CONTINUE;
            }
        });
    }


}
