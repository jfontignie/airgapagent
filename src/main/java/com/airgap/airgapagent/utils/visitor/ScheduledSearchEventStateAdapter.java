package com.airgap.airgapagent.utils.visitor;

import com.airgap.airgapagent.domain.ExactMatchResult;
import com.airgap.airgapagent.utils.CrawlState;
import com.airgap.airgapagent.utils.IntervalRunner;

import java.time.Duration;

/**
 * com.airgap.airgapagent.utils.visitor
 * Created by Jacques Fontignie on 11/3/2021.
 */

public abstract class ScheduledSearchEventStateAdapter<T> extends SearchEventAdapter<T> {

    private final IntervalRunner runner;

    ScheduledSearchEventStateAdapter(int secondsInterval, boolean runFirstCall) {
        this.runner = IntervalRunner.of(Duration.ofSeconds(secondsInterval), runFirstCall);
    }


    ScheduledSearchEventStateAdapter(int secondsInterval) {
        this(secondsInterval, true);
    }

    @Override
    public final void onFound(CrawlState<T> crawlState, ExactMatchResult<T> result) {
        runner.trigger(count -> onFoundEvent(crawlState, result));
    }

    /**
     * @param crawlState : the crawler state
     * @param result     The current value
     */
    abstract void onFoundEvent(CrawlState<T> crawlState, ExactMatchResult<T> result);
}
