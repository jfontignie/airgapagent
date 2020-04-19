package com.airgap.airgapagent.synchro;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

/**
 * com.airgap.airgapagent.synchro
 * Created by Jacques Fontignie on 4/17/2020.
 */
public class CopyWork implements Work {


    private Path target;

    public CopyWork() {
        //For jackson
    }

    public CopyWork(Path target) {
        this.target = target;
    }

    public CopyWork(String targetFolder) {
        setTarget(targetFolder);
    }

    public String getTarget() {
        return target.toString();
    }

    public void setTarget(String targetFolder) {
        this.target = Path.of(targetFolder);
    }

    @Override
    public void init() throws IOException {
        if (Files.notExists(target)) {
            Files.createDirectory(target);
        } else {
            if (Files.isRegularFile(target)) {
                throw new IllegalStateException("Not a folder");
            }
        }
    }

    @Override
    public void call(PathInfo path) throws IOException {
        Objects.requireNonNull(target, "Init must be called");
        Path parent = path.getRelative().getParent();
        if (parent != null) {
            Path parentPath = target.resolve(parent);
            Files.createDirectories(parentPath);
        }
        Path targetPath = target.resolve(path.getRelative());
        Files.copy(path.getOriginalPath(), targetPath, StandardCopyOption.REPLACE_EXISTING);
    }
}
