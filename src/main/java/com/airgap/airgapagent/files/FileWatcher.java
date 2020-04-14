package com.airgap.airgapagent.files;

import java.io.IOException;
import java.nio.file.*;
import java.util.EnumSet;
import java.util.Set;
import java.util.stream.Collectors;

import static java.nio.file.StandardWatchEventKinds.*;

/**
 * com.airgap.airgapagent.files
 * Created by Jacques Fontignie on 4/14/2020.
 */
public class FileWatcher {
    private final Path path;
    private final WatchEvent.Kind<?>[] kinds;

    public FileWatcher(Path path, Set<WatchEvent.Kind<?>> kinds) {
        this.path = path;
        this.kinds = kinds.toArray(new WatchEvent.Kind[0]);
    }

    public WatchService watch() throws IOException {

        WatchService watchService = FileSystems.getDefault().newWatchService();
        WatchKey key = path.register(watchService, kinds);
        if (!key.isValid())
            throw new IOException("Impossible to build WatchKey to listen to folder");
        return watchService;
    }

    public Path getPath() {
        return path;
    }

    public static class Builder {

        private final Path path;
        private EnumSet<WatchType> watchTypes = EnumSet.allOf(WatchType.class);

        private Builder(Path path) {
            this.path = path;
        }

        public static Builder aNewWatch(Path path) {
            return new Builder(path);
        }

        public Builder setWatchTypes(EnumSet<WatchType> watchTypes) {
            this.watchTypes = watchTypes;
            return this;
        }

        public FileWatcher build() {
            return new FileWatcher(path,
                    watchTypes.stream()
                            .map(type -> {
                                switch (type) {
                                    case CREATED:
                                        return ENTRY_CREATE;
                                    case DELETED:
                                        return ENTRY_DELETE;
                                    case MODIFIED:
                                        return ENTRY_MODIFY;
                                    default:
                                        return null;
                                }
                            })
                            .collect(Collectors.toUnmodifiableSet())
            );
        }
    }
}
