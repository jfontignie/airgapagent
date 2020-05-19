package com.airgap.airgapagent.synchro.predicate;

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
        @JsonSubTypes.Type(value = VersionPredicate.class, name = PredicateConstants.RECENT),
        @JsonSubTypes.Type(value = FileTimePredicate.class, name = PredicateConstants.FILE_TIME),

})
public interface Predicate<T> extends Closeable {

    void init();

    boolean call(T t) throws IOException;
}
