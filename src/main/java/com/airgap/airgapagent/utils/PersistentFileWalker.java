package com.airgap.airgapagent.utils;

import com.airgap.airgapagent.service.FileWalkerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.io.File;
import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * com.airgap.airgapagent.service
 * Created by Jacques Fontignie on 5/30/2020.
 */
public class PersistentFileWalker {
    private static final Logger log = LoggerFactory.getLogger(PersistentFileWalker.class);

    private final FileWalkerService fileWalkerService;
    private final File stateLocation;
    private final Duration saveInterval;


    public PersistentFileWalker(FileWalkerService fileWalkerService, File stateLocation, Duration saveInterval) {
        this.fileWalkerService = fileWalkerService;
        this.stateLocation = stateLocation;
        this.saveInterval = saveInterval;

    }

    public Flux<File> listFiles(FileWalkerContext fileWalkerContext) {

        StateStore stateStore = new StateStore(stateLocation);
        stateStore.load(fileWalkerContext);

        IntervalRunner runner = IntervalRunner.of(saveInterval, true);

        AtomicInteger counterProcessed = new AtomicInteger();

        return fileWalkerService.listFiles(fileWalkerContext)
                .doOnNext(f -> {
                    counterProcessed.incrementAndGet();
                    runner.trigger(() -> {
                        log.info("Running {} / {} ({} %)",
                                counterProcessed,
                                fileWalkerContext.getVisited(),
                                counterProcessed.get() * 100 / fileWalkerContext.getVisited());
                        stateStore.save(fileWalkerContext);
                    });
                })
                .doOnComplete(stateStore::clear);
    }
}
