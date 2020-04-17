package com.airgap.airgapagent.synchro;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

/**
 * com.airgap.airgapagent.synchro
 * Created by Jacques Fontignie on 4/17/2020.
 */
public class CopyTask extends AbstractTask {

    private String targetFolder;

    private transient Path target;

    public CopyTask() {
        super(TaskType.COPY);
    }

    public CopyTask(String name, String targetFolder) {
        this();
        setName(name);
        setTargetFolder(targetFolder);
    }

    public String getTargetFolder() {
        return targetFolder;
    }

    public void setTargetFolder(String targetFolder) {
        this.targetFolder = targetFolder;
    }

    @Override
    public void init() throws IOException {
        target = Path.of(targetFolder);
        if (Files.notExists(target)) {
            Files.createDirectory(target);
        } else {
            if (Files.isRegularFile(target)) {
                throw new IllegalStateException("Not a folder");
            }
        }
    }

    @Override
    public void call(Path baseFolder, Path path) throws IOException {
        Path parent = path.getParent();
        if (parent != null) {
            Path parentPath = target.resolve(parent);
            Files.createDirectories(parentPath);
        }
        Path targetPath = target.resolve(path);
        Files.copy(baseFolder.resolve(path), targetPath, StandardCopyOption.REPLACE_EXISTING);
    }
}
