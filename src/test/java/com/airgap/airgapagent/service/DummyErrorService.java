package com.airgap.airgapagent.service;

import com.airgap.airgapagent.utils.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * com.airgap.airgapagent.service
 * Created by Jacques Fontignie on 5/31/2020.
 */
public class DummyErrorService implements ErrorService {
    private static final Logger log = LoggerFactory.getLogger(DummyErrorService.class);

    @Override
    public void error(Object source, String message, Throwable e) {
        log.info(" {} - {} - {}", source, message, ExceptionUtils.expand(e));
    }
}
