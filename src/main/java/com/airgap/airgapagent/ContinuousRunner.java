package com.airgap.airgapagent;

import com.airgap.airgapagent.configuration.AbstractScanConfiguration;
import com.airgap.airgapagent.utils.StateSaver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.Instant;
import java.util.Date;
import java.util.function.Consumer;

/**
 * com.airgap.airgapagent
 * Created by Jacques Fontignie on 11/13/2021.
 */
public class ContinuousRunner<T> implements Runner<T> {


    private static final Logger log = LoggerFactory.getLogger(ContinuousRunner.class);
    private static final String CONTINUOUS_STATE = "continuous_state";

    @Override
    public void run(AbstractScanConfiguration<T> configuration, Consumer<AbstractScanConfiguration<T>> consumer) {
        String value = StateSaver.get(configuration.getStateLocation(), CONTINUOUS_STATE);
        if (value != null) {
            Instant lastScan = Instant.ofEpochMilli(Long.parseLong(value));
            configuration.setLaterThan(Date.from(lastScan));
        }
        Instant previous = Instant.now();
        log.info("Performing scan from scratch");
        //noinspection InfiniteLoopStatement
        do {
            consumer.accept(configuration);
            configuration.setLaterThan(Date.from(previous));

            try {
                StateSaver.set(configuration.getStateLocation(),
                        CONTINUOUS_STATE,
                        String.valueOf(previous.toEpochMilli()));
            } catch (IOException e) {
                log.warn("Impossible to save state. If you restart the service, the state will be lost");
            }
            previous = Instant.now();

            log.info("Restarting scan with objects older than {}", previous);
        } while (true);

    }

}
