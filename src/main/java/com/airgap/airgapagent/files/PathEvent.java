package com.airgap.airgapagent.files;

import java.nio.file.Path;
import java.util.StringJoiner;

/**
 * com.airgap.airgapagent.files
 * Created by Jacques Fontignie on 4/14/2020.
 */
public class PathEvent {

    private final WatchType eventType;
    private final Path path;


    public PathEvent(WatchType eventType, Path path) {
        this.eventType = eventType;
        this.path = path;
    }

    public WatchType getEventType() {
        return eventType;
    }

    public Path getPath() {
        return path;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", PathEvent.class.getSimpleName() + "[", "]")
                .add("eventType=" + eventType)
                .add("path=" + path)
                .toString();
    }
}
