package com.airgap.airgapagent.service;

import com.airgap.airgapagent.utils.DataReader;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * com.airgap.airgapagent.service
 * Created by Jacques Fontignie on 5/31/2020.
 */
@Service
public class FileCrawlService implements CrawlService<File> {

    private final ContentReaderService contentReaderService;
    private final ErrorService errorService;

    public FileCrawlService(ContentReaderService contentReaderService, ErrorService errorService) {
        this.contentReaderService = contentReaderService;
        this.errorService = errorService;
    }

    @Override
    public Optional<DataReader<File>> getContentReader(File file) {
        try {
            return Optional.of(contentReaderService.getContent(file));
        } catch (IOException e) {
            errorService.error(file, "impossible to get content", e);
            return Optional.empty();
        }
    }

    @Override
    public boolean isLeaf(File current) {
        return current.isFile();
    }

    @Override
    public List<File> listChildren(File current) {
        File[] children = current.listFiles();
        return children == null ?
                Collections.emptyList() : Arrays.stream(children).collect(Collectors.toList());
    }

    public void copy(File root, File source, File destinationRoot) throws IOException {
        File newFile = buildLocation(root, source, destinationRoot);
        FileUtils.copyFile(source, newFile, true);
    }

    public File buildLocation(File root, File source, File destinationRoot) {
        return Path.of(destinationRoot.toPath().toString(), root.toPath().relativize(source.toPath()).toString()).toFile();
    }
}
