package com.airgap.airgapagent.files;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.WatchEvent;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;

/**
 * com.airgap.airgapagent.files
 * Created by Jacques Fontignie on 4/12/2020.
 */
public class PathCreateWatcher extends PathChangeWatcher {
    PathCreateWatcher(Path path) throws IOException {
        super(path, new WatchEvent.Kind[]{ENTRY_CREATE});
    }
}
