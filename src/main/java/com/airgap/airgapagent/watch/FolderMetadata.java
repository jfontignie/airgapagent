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
    public FolderMetadata(@JsonProperty("root") String root,
                          @JsonProperty("type") Type type,
                          @JsonProperty("relative") String relative) {
        super(root, type, relative);
    }

    public FolderMetadata(Path root, Path path) {
        super(root, path);
    }
}
