package com.airgap.airgapagent.batch;

import com.airgap.airgapagent.synchro.utils.PathInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemReader;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.stream.Stream;

/**
 * com.airgap.airgapagent.batch
 * Created by Jacques Fontignie on 4/28/2020.
 */
public class FolderItemReader implements ItemReader<PathInfo> {

    private static final Logger log = LoggerFactory.getLogger(FolderItemReader.class);
    private final Deque<Path> stack = new ArrayDeque<>();
    private final Path folder;


    public FolderItemReader(Path folder) {
        this.folder = folder;
        stack.push(folder);
    }

    @Override
    public PathInfo read() throws Exception {

        if (stack.isEmpty()) {
            return null;
        }
        while (!stack.isEmpty()) {
            Path current = stack.pop();
            log.debug("Reading : {}", current);

            if (Files.isRegularFile(current)) {
                return new PathInfo(folder, current);
            }

            Stream<Path> stream = Files.list(current);
            try (stream) {
                stream.forEach(stack::push);
            }

        }
        return null;
    }
}
