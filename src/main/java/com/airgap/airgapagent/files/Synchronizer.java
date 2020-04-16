package com.airgap.airgapagent.files;

import com.airgap.airgapagent.watch.Difference;

import java.io.IOException;
import java.util.List;

/**
 * com.airgap.airgapagent.files
 * Created by Jacques Fontignie on 4/16/2020.
 */
public interface Synchronizer {
    void synchronize(List<Difference> differences) throws IOException;
}
