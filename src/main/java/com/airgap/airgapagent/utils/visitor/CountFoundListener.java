package com.airgap.airgapagent.utils.visitor;

import com.airgap.airgapagent.domain.ExactMatchResult;
import com.airgap.airgapagent.utils.CrawlState;
import com.airgap.airgapagent.utils.IntervalRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

/**
 * com.airgap.airgapagent.utils.visitor
 * Created by Jacques Fontignie on 11/3/2021.
 */
public class CountFoundListener<T> extends SearchEventAdapter<T> {
    private static final Logger log = LoggerFactory.getLogger(CountFoundListener.class);

    private final IntervalRunner interval;

    public CountFoundListener(int numSeconds) {
        interval = IntervalRunner.of(Duration.ofSeconds(numSeconds), false);
    }

    @Override
    public void onFound(CrawlState<T> crawlState, ExactMatchResult<T> result) {
        interval.trigger(count -> log.info("Elements found so far: {}", count));
    }
}
