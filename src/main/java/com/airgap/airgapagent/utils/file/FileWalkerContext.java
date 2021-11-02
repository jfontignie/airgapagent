package com.airgap.airgapagent.utils.file;

import com.airgap.airgapagent.utils.filters.AlwaysWalkFilter;
import com.airgap.airgapagent.utils.filters.WalkerFilter;

import java.io.File;

/**
 * com.airgap.airgapagent.service
 * Created by Jacques Fontignie on 5/24/2020.
 */
public class FileWalkerContext {
    private final WalkerFilter<File> directoryFilter;
    private final WalkerFilter<File> fileFilter;
    private final File file;
    private File reference;

    public FileWalkerContext(File file, WalkerFilter<File> fileFilter, WalkerFilter<File> directoryFilter) {
        this.file = file;
        this.fileFilter = fileFilter;
        this.directoryFilter = directoryFilter;
    }

    private FileWalkerContext(File file) {
        this(file, new AlwaysWalkFilter<>(), new AlwaysWalkFilter<>());
    }

    public static FileWalkerContext of(File file) {
        return new FileWalkerContext(file);
    }

    public WalkerFilter<File> getDirectoryFilter() {
        return directoryFilter;
    }

    public WalkerFilter<File> getFileFilter() {
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
