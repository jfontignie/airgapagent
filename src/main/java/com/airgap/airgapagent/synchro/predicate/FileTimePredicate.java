package com.airgap.airgapagent.synchro.predicate;

import com.airgap.airgapagent.synchro.utils.PathInfo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.FileTime;
import java.time.Duration;
import java.time.Instant;
import java.util.Objects;

/**
 * com.airgap.airgapagent.synchro.predicate
 * Created by Jacques Fontignie on 5/10/2020.
 */
public class FileTimePredicate extends AbstractPredicate<PathInfo> {

    private String rule;
    private Duration duration;

    public String getOlderThan() {
        return rule;
    }

    public void setOlderThan(String rule) {
        this.rule = rule;
    }

    @Override
    public void init() {
        Objects.requireNonNull(rule, "The rule has not been defined");
        duration = DurationHelper.simpleParse(rule);
    }

    @Override
    public boolean call(PathInfo path) throws IOException {
        Objects.requireNonNull(duration, "Init method not called");
        FileTime lastModifiedTime = Files.getLastModifiedTime(path.getOriginalPath());
        return Duration.between(lastModifiedTime.toInstant(), Instant.now()).compareTo(duration) >= 0;
    }

}
