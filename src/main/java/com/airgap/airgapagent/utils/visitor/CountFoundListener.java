package com.airgap.airgapagent.utils.visitor;

import com.airgap.airgapagent.domain.ExactMatchResult;
import com.airgap.airgapagent.utils.CrawlState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * com.airgap.airgapagent.utils.visitor
 * Created by Jacques Fontignie on 11/3/2021.
 */
public class CountFoundListener<T> extends ScheduledSearchEventStateAdapter<T> {
    private static final Logger log = LoggerFactory.getLogger(CountFoundListener.class);


    public CountFoundListener(int numSeconds) {
        super(numSeconds);
    }

    @Override
    void onFoundEvent(CrawlState<T> crawlState, ExactMatchResult<T> result) {
        log.info("Elements found so far {}", crawlState.getFound());
    }

    @Override
    public void onClose(CrawlState<T> crawlState) {
        log.info("Elements found in total: {}", crawlState.getFound());
    }
}
