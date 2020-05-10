package com.airgap.airgapagent.synchro.work;

import com.airgap.airgapagent.synchro.utils.PathInfo;
import org.apache.commons.io.FileUtils;

import java.io.IOException;
import java.nio.file.Files;

/**
 * com.airgap.airgapagent.synchro
 * Created by Jacques Fontignie on 4/17/2020.
 */
public class DeleteWork extends AbstractWork {

    @Override
    public void call(PathInfo path) throws IOException {
        if (Files.isRegularFile(path.getOriginalPath())) {
            Files.delete(path.getOriginalPath());
        } else {
            FileUtils.deleteDirectory(path.getOriginalPath().toFile());
        }
    }
}
