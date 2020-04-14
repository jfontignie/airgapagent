package com.airgap.airgapagent.files;

import java.io.IOException;
import java.nio.file.WatchService;

/**
 * com.airgap.airgapagent.files
 * Created by Jacques Fontignie on 4/14/2020.
 */
public interface Watcher {

    WatchService watch() throws IOException;
}
