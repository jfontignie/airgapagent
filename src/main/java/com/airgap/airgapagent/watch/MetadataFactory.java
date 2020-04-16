package com.airgap.airgapagent.watch;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * com.airgap.airgapagent.files
 * Created by Jacques Fontignie on 4/15/2020.
 */
public class MetadataFactory {

    private MetadataFactory() {
        //Nothing to do
    }

    public static Metadata analyze(Path path) throws IOException {

        if (Files.isRegularFile(path)) {
            return new FileMetadata(path);
        } else {
            return new FolderMetadata(path);
        }
    }

}
