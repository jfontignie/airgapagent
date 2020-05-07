package com.airgap.airgapagent.synchro.work;

import com.airgap.airgapagent.synchro.utils.PathInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.io.Closeable;
import java.io.IOException;

/**
 * com.airgap.airgapagent.synchro
 * Created by Jacques Fontignie on 4/17/2020.
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = CopyWork.class, name = WorkConstants.COPY_NAME),
        @JsonSubTypes.Type(value = ConditionalWork.class, name = WorkConstants.CONDITION_NAME),
        @JsonSubTypes.Type(value = SyslogWork.class, name = WorkConstants.SYSLOG_NAME),
        @JsonSubTypes.Type(value = SequentialWork.class, name = WorkConstants.SEQUENCE_NAME),
})
public interface Work extends Closeable {

    void init() throws IOException;

    void call(PathInfo path) throws IOException;

}
