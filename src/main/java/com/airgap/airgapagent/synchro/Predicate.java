package com.airgap.airgapagent.synchro;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.io.Closeable;
import java.io.IOException;

/**
 * com.airgap.airgapagent.synchro
 * Created by Jacques Fontignie on 4/19/2020.
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = RegexPredicate.class, name = PredicateConstants.REGEX),
        @JsonSubTypes.Type(value = ExtensionPredicate.class, name = PredicateConstants.EXTENSION),

})
public interface Predicate extends Closeable {

    void init() throws IOException;

    boolean call(PathInfo path) throws IOException;
}
