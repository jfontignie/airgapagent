package com.airgap.airgapagent.watch;

import com.airgap.airgapagent.files.Type;

import java.nio.file.Path;

/**
 * com.airgap.airgapagent.files
 * Created by Jacques Fontignie on 4/15/2020.
 */
public class FolderMetadata extends Metadata {

    public FolderMetadata() {
        super(Type.DIRECTORY);
    }

    public FolderMetadata(Path root, Path path) {
        super(root, path);
    }


}
