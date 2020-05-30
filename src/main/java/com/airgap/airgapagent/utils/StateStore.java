package com.airgap.airgapagent.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.stream.Stream;

/**
 * com.airgap.airgapagent.service
 * Created by Jacques Fontignie on 5/30/2020.
 */
public class StateStore {

    private static final Logger log = LoggerFactory.getLogger(StateStore.class);

    private final File stateLocation;

    public StateStore(File stateLocation) {
        this.stateLocation = stateLocation;
    }

    public void load(FileWalkerContext context) {
        if (stateLocation.exists()) {
            try {
                try (Stream<String> lines = Files.lines(stateLocation.toPath())) {
                    lines.findFirst()
                            .ifPresent(line -> {
                                log.info("Last state detected: {}", line);
                                context.setLastFileToVisit(new File(line));
                            });
                }
            } catch (IOException e) {
                log.error("Impossible to read the state file", e);
            }

        }
    }

    public void save(FileWalkerContext context) {
        String state = context.getRefefenceFile().toString();
        save(state);
    }

    private void save(String state) {
        try {
            Files.writeString(stateLocation.toPath(), state);
        } catch (IOException e) {
            log.error("Impossible to save state", e);
        }
    }

    public void clear() {
        save("");
    }
}
