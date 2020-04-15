package com.airgap.airgapagent.files;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.IOException;
import java.nio.file.Path;

/**
 * com.airgap.airgapagent.files
 * Created by Jacques Fontignie on 4/15/2020.
 */
public class FolderMetadata extends Metadata {
    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public FolderMetadata(@JsonProperty("fileTime") long fileTime,
                          @JsonProperty("path") Path path,
                          @JsonProperty("type") Type type,
                          @JsonProperty("fileName") String fileName) {
        super(fileTime, path, type, fileName);
    }

    public FolderMetadata(Path path) throws IOException {
        super(path);
    }
}
