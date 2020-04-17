package com.airgap.airgapagent.synchro;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;

/**
 * com.airgap.airgapagent.synchro
 * Created by Jacques Fontignie on 4/17/2020.
 */
public class Synchronizer implements Serializable {

    private static final Logger logger = LoggerFactory.getLogger(Synchronizer.class);

    private transient Path baseFolder;
    private int earlierThan;
    private List<Task> tasks;

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

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public void run() throws IOException {
        for (Task task : tasks) {
            task.init();
        }

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
                for (Task task : tasks) {
                    task.call(baseFolder, baseFolder.relativize(path));
                }
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
