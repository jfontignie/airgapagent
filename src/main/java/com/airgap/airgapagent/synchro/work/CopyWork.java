package com.airgap.airgapagent.synchro.work;

import com.airgap.airgapagent.synchro.utils.PathInfo;
import com.airgap.airgapagent.utils.FileUtils;

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

    private static final int MAX_RETRIES = 100;

    private Path target;

    private boolean override = true;


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

    public boolean isOverride() {
        return override;
    }

    public void setOverride(boolean override) {
        this.override = override;
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
        if (!override && Files.exists(targetPath)) {
            Path expectedPath = null;
            for (int i = 0; i < MAX_RETRIES; i++) {
                expectedPath = FileUtils.withRandomTimeStamp(targetPath);
                if (Files.notExists(expectedPath)) {
                    break;
                }
                expectedPath = null;
            }
            if (expectedPath == null) {
                throw new IllegalStateException("Impossible to find a temporary name");
            }
            Files.copy(path.getOriginalPath(), FileUtils.withRandomTimeStamp(expectedPath), StandardCopyOption.REPLACE_EXISTING);
        } else {
            Files.copy(path.getOriginalPath(), targetPath, StandardCopyOption.REPLACE_EXISTING);
        }
    }
}
