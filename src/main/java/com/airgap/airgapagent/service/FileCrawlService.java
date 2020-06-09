package com.airgap.airgapagent.service;

import com.airgap.airgapagent.configuration.CopyOption;
import com.airgap.airgapagent.utils.DataReader;
import org.apache.commons.compress.utils.FileNameUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.*;
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

    public void copy(File root, File source, File destinationRoot, Set<CopyOption> copyOptions) throws IOException {
        File newFile;
        if (copyOptions.contains(CopyOption.FLAT_HIERARCHY)) {
            newFile = Path.of(destinationRoot.toPath().toString(), source.getName()).toFile();
        } else {
            newFile = buildLocation(root, source, destinationRoot);
        }
        if (copyOptions.contains(CopyOption.RENAME_ON_COLLISION)) {
            newFile = unify(newFile);
        }

        FileUtils.copyFile(source, newFile, true);
    }


    public void copy(File root, File source, File destinationRoot) throws IOException {
        copy(root, source, destinationRoot, Set.of());
    }

    private File unify(File newFile) {
        String filename = newFile.toString();
        for (int i = 0; i < 10; i++) {
            if (!newFile.exists()) {
                return newFile;
            }

            String newName = newFile.getParent() + "/" + FileNameUtils.getBaseName(filename) + "_" + getTimeStamp() + "." + FileNameUtils.getExtension(filename);
            newFile = new File(newName);
        }
        throw new IllegalStateException("Impossible to find a proper name");
    }

    private String getTimeStamp() {
        return new SimpleDateFormat("HHmmss_SSS").format(new Date());
    }

    public File buildLocation(File root, File source, File destinationRoot) {
        return Path.of(destinationRoot.toPath().toString(), root.toPath().relativize(source.toPath()).toString()).toFile();
    }

}
