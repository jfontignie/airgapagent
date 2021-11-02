package com.airgap.airgapagent.utils.visitor;

import com.airgap.airgapagent.utils.IntervalRunner;
import com.airgap.airgapagent.utils.exceptions.ExceptionUtils;
import com.airgap.airgapagent.utils.exceptions.ThrowableStatement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

/**
 * com.airgap.airgapagent.service
 * Created by Jacques Fontignie on 5/30/2020.
 */
public class ScheduledStateVisitor implements Closeable {
    private static final Logger log = LoggerFactory.getLogger(ScheduledStateVisitor.class);


    private final Duration saveInterval;
    private final List<StateVisitor> visitors;

    private IntervalRunner runner;
    private Instant start;

    public ScheduledStateVisitor(Duration saveInterval, List<StateVisitor> visitors) {
        this.saveInterval = saveInterval;
        this.visitors = visitors;
    }


    public void init() {
        runner = IntervalRunner.of(saveInterval, true);
        start = Instant.now();
        visitors.forEach(StateVisitor::init);
    }

    public void visit() {
        Objects.requireNonNull(start, "the object has not been initialized");

        runner.trigger(analysed -> {
            log.debug("Trigger has been hit for action");
            visitors.forEach(v -> v.visit(analysed));
        });
    }


    public void close() {
        for (StateVisitor v : visitors) {
            ExceptionUtils.run(
                    (ThrowableStatement<Exception>) v::close, e -> log.error("Impossible to close visitor", e));
        }
    }


}
