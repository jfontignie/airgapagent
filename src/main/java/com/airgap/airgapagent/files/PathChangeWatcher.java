package com.airgap.airgapagent.files;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.stream.Collectors;

import static java.nio.file.StandardWatchEventKinds.*;

/**
 * com.airgap.airgapagent.triggers
 * Created by Jacques Fontignie on 4/12/2020.
 */
public class PathChangeWatcher {

    private final Path path;
    private final WatchService watcher;
    private final WatchEvent.Kind<?>[] events;

    public PathChangeWatcher(Path path) throws IOException {
        this(path, new WatchEvent.Kind[]{ENTRY_CREATE, ENTRY_DELETE,ENTRY_MODIFY});
    }


    PathChangeWatcher(Path path, WatchEvent.Kind<?>[] events) throws IOException {
        this.path = path;
        this.events = events;
        watcher = FileSystems.getDefault().newWatchService();
    }

    public void execute() throws IOException, InterruptedException {
        WatchKey key = path.register(watcher,events);
        key = watcher.take();
        List<WatchEvent<?>> events = key.pollEvents();
        List<Path> list = events.stream().map(e -> ((WatchEvent<Path>) e).context()).collect(Collectors.toList());
    }
}
