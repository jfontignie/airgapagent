package com.airgap.airgapagent.service;

import com.airgap.airgapagent.utils.ConstantsTest;
import com.airgap.airgapagent.utils.FileWalkerContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.scheduler.Schedulers;

import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * com.airgap.airgapagent.service
 * Created by Jacques Fontignie on 5/24/2020.
 */
class FileWalkerServiceTest {

    private static final Logger log = LoggerFactory.getLogger(FileWalkerServiceTest.class);

    @Test
    void testPath() {
        Path p0 = Path.of("tast");
        Path p1 = Path.of("test/abcd");
        Path p2 = Path.of("test/efgh");
        Assertions.assertTrue(p0.compareTo(p1) < 0);
        Assertions.assertTrue(p1.compareTo(p2) < 0);
        Assertions.assertTrue(p0.compareTo(p2) < 0);
    }

    @Test
    void listFiles() {
        FileWalkerService service = new FileWalkerService();
        FileWalkerContext context = FileWalkerContext.of(ConstantsTest.SAMPLE_FOLDER);
        AtomicInteger counter = new AtomicInteger();

        service.listFiles(context).subscribe(f -> {
            log.info("Found {}, ", f);
            counter.incrementAndGet();
        }).dispose();

        log.info("Managed first loop: {}", counter);
        AtomicInteger countStop = new AtomicInteger();

        context.reset();

        service.listFiles(context)
                .subscribe(f -> {
                    log.info("First loop {}, ", f);
                    if (countStop.incrementAndGet() == 5) {
                        throw new IllegalStateException("close first loop");
                    }
                }, throwable -> log.info("exception expected")).dispose();

        log.info("Finished at {} on {}", countStop, context.getRefefenceFile());

        service.listFiles(context).subscribe(f -> {
            log.info("Second loop {}, ", f);
            countStop.incrementAndGet();
        }).dispose();

        Assertions.assertEquals(counter.get() + 1, countStop.get());

        context.reset();

        countStop.set(0);
        service.listFiles(context)
                .parallel()
                .runOn(Schedulers.parallel())
                .doOnNext(f -> {
                    log.info("Start parallel loop {}, ", f);
                    long start = System.currentTimeMillis();
                    //noinspection StatementWithEmptyBody
                    while (System.currentTimeMillis() - start < 1000) {
                        //Do nothing
                    }
                    countStop.incrementAndGet();
                    log.info("End parallel loop {}, ", f);

                }).sequential().blockLast();
        log.info("Count {}", countStop.get());
        Assertions.assertEquals(counter.get(), countStop.get());

    }


}
