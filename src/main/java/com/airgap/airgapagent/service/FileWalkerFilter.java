package com.airgap.airgapagent.service;

import java.io.File;

/**
 * com.airgap.airgapagent.service
 * Created by Jacques Fontignie on 5/23/2020.
 */
public interface FileWalkerFilter {

    boolean accept(File file);
}
