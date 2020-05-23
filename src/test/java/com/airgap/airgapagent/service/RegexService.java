package com.airgap.airgapagent.service;

import reactor.core.Disposable;

import java.io.InputStream;
import java.util.List;
import java.util.function.Consumer;

/**
 * com.airgap.airgapagent.service
 * Created by Jacques Fontignie on 5/23/2020.
 */
public interface RegexService {
    Disposable ifPresent(InputStream content, List<String> regexes, Consumer<String> consumer);
}
