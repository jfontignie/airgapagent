package com.airgap.airgapagent.service;

import com.airgap.airgapagent.batch.ExactMatchBatchConfiguration;
import com.airgap.airgapagent.utils.ErrorWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import java.io.File;
import java.io.IOException;

/**
 * com.airgap.airgapagent.service
 * Created by Jacques Fontignie on 5/31/2020.
 */
@Service
public class ErrorServiceImpl implements ErrorService {

    private static final Logger log = LoggerFactory.getLogger(ErrorServiceImpl.class);
    private final ErrorWriter errorWriter;

    public ErrorServiceImpl(Environment environment) throws IOException {
        String requiredProperty = environment.getRequiredProperty(ExactMatchBatchConfiguration.MATCH_ERROR_FILE);
        File errorFile = new File(requiredProperty);

        log.info("Error file will be in {}", errorFile);
        errorWriter = new ErrorWriter(errorFile);
    }

    @Override
    public void error(Object source, String message, Throwable e) {
        log.error("Concerned {} - {}", source, message, e);
        errorWriter.trigger(source, message, e);
    }

    @PreDestroy
    public void tearDown() throws IOException {
        errorWriter.close();
    }
}
