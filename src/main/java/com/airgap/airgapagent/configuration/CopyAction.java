package com.airgap.airgapagent.configuration;

import java.util.List;

/**
 * com.airgap.airgapagent.configuration
 * Created by Jacques Fontignie on 6/8/2020.
 */
public interface CopyAction<T> {

    List<CopyOption> getCopyOptions();

    T getTarget();
}
