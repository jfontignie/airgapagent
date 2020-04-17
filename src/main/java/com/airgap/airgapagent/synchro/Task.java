package com.airgap.airgapagent.synchro;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.io.IOException;
import java.io.Serializable;

/**
 * com.airgap.airgapagent.synchro
 * Created by Jacques Fontignie on 4/17/2020.
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = CopyTask.class, name = TaskType.Constants.COPY_NAME),
        @JsonSubTypes.Type(value = RegexTask.class, name = TaskType.Constants.REGEX_NAME),
        @JsonSubTypes.Type(value = SyslogTask.class, name = TaskType.Constants.SYSLOG_NAME),
})
public interface Task extends Serializable {
    String getName();

    TaskType getTaskType();

    void init() throws IOException;

}
