package com.airgap.airgapagent.utils;

import com.airgap.airgapagent.service.FileWalkerFilter;
import com.airgap.airgapagent.service.FileWalkerService;

import java.io.File;

/**
 * com.airgap.airgapagent.service
 * Created by Jacques Fontignie on 5/24/2020.
 */
public class FileWalkerContext {
    private final FileWalkerFilter directoryFilter;
    private final FileWalkerFilter fileFilter;
    private final File file;
    private File reference;

    public FileWalkerContext(File file, FileWalkerFilter fileFilter, FileWalkerFilter directoryFilter) {
        this.file = file;
        this.fileFilter = fileFilter;
        this.directoryFilter = directoryFilter;
    }

    private FileWalkerContext(File file) {
        this(file, FileWalkerService.TRUE, FileWalkerService.TRUE);
    }

    public static FileWalkerContext of(File file) {
        return new FileWalkerContext(file);
    }

    public FileWalkerFilter getDirectoryFilter() {
        return directoryFilter;
    }

    public FileWalkerFilter getFileFilter() {
        return fileFilter;
    }

    public void setLastFileToVisit(File f) {
        this.reference = f;
    }

    public File getRefefenceFile() {
        return reference;
    }

    public File getRootFile() {
        return file;
    }

    public void reset() {
        this.reference = null;
    }

}
