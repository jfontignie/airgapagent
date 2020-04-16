package com.airgap.airgapagent.watch;

import com.airgap.airgapagent.files.Type;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.nio.file.Path;

/**
 * com.airgap.airgapagent.files
 * Created by Jacques Fontignie on 4/15/2020.
 */
public class FolderMetadata extends Metadata {
    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public FolderMetadata(@JsonProperty("path") Path path,
                          @JsonProperty("type") Type type,
                          @JsonProperty("fileName") String fileName) {
        super(path, type, fileName);
    }

    public FolderMetadata(Path path) {
        super(path);
    }
}
