package com.airgap.airgapagent.synchro;

import com.airgap.airgapagent.synchro.utils.PathInfo;
import com.airgap.airgapagent.synchro.work.Work;
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
public class Synchronizer extends SynchroConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(Synchronizer.class);

    public Synchronizer(Path baseFolder, Work flow) {
        super(baseFolder, flow);
    }

    public Synchronizer() {
        super();
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
