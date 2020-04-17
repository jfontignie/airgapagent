package com.airgap.airgapagent.synchro;

import static com.airgap.airgapagent.synchro.TaskType.Constants.*;

/**
 * com.airgap.airgapagent.synchro
 * Created by Jacques Fontignie on 4/17/2020.
 */

public enum TaskType {

    SYSLOG(SYSLOG_NAME), COPY(COPY_NAME), REGEX(REGEX_NAME);


    TaskType(String name) {
        if (!name().equals(name)) {
            throw new IllegalStateException("Not matching");
        }
    }

    public static class Constants {

        static final String SYSLOG_NAME = "SYSLOG";
        static final String COPY_NAME = "COPY";
        static final String REGEX_NAME = "REGEX";

        private Constants() {
            //Nothing to do
        }
    }
}
