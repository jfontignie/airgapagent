package com.airgap.airgapagent;

import com.airgap.airgapagent.configuration.AbstractScanConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.Date;
import java.util.function.Consumer;

/**
 * com.airgap.airgapagent
 * Created by Jacques Fontignie on 11/13/2021.
 */
public class ContinuousRunner<T> implements Runner<T> {


    private static final Logger log = LoggerFactory.getLogger(ContinuousRunner.class);

    @Override
    public void run(AbstractScanConfiguration<T> configuration, Consumer<AbstractScanConfiguration<T>> consumer) {
        Instant previous = Instant.now();
        log.info("Performing scan from scratch");
        //noinspection InfiniteLoopStatement
        do {
            consumer.accept(configuration);
            configuration.setLaterThan(Date.from(previous));
            previous = Instant.now();

            log.info("Restarting scan with objects older than {}", previous);
        } while (true);
    }

}
