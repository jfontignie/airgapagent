package com.airgap.airgapagent.service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * com.airgap.airgapagent.service
 * Created by Jacques Fontignie on 5/9/2020.
 */
@Service
public class FileWrapperService {


    public boolean isRegularFile(Path currentPath) {
        return Files.isRegularFile(currentPath);
    }

    public List<Path> list(Path currentPath) throws IOException {
        try (Stream<Path> stream = Files.list(currentPath)) {
            return stream.collect(Collectors.toList());
        }
    }

    public FileTime getLastModifiedTime(Path currentPath) throws IOException {
        return Files.getLastModifiedTime(currentPath);
    }
}
