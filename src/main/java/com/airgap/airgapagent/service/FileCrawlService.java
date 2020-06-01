package com.airgap.airgapagent.service;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.Reader;
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

    public FileCrawlService(ContentReaderService contentReaderService) {
        this.contentReaderService = contentReaderService;
    }

    @Override
    public Optional<Reader> getContentReader(File file) {
        return contentReaderService.getContent(file);
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
}
