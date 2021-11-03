package com.airgap.airgapagent.utils.visitor;

import com.airgap.airgapagent.domain.ExactMatchResult;
import com.airgap.airgapagent.utils.CrawlState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

/**
 * com.airgap.airgapagent.service
 * Created by Jacques Fontignie on 5/30/2020.
 */
public class ProgressLogStateListener<T> extends ScheduledSearchEventStateAdapter<T> {
    private static final Logger log = LoggerFactory.getLogger(ProgressLogStateListener.class);

    private Instant start;

    public ProgressLogStateListener(int interval) {
        super(interval);
    }

    @Override
    public void onInit() {
        start = Instant.now();
    }

    @Override
    public void onFound(int analysed, CrawlState<T> crawlState, ExactMatchResult<T> notUsed) {

        int crawled = crawlState.getVisited();
        String progress = "n/a";
        String estimate = "n/a";
        String crawlspeed = "n/a";
        String analysisSpeed = "n/a";

        long seconds = ChronoUnit.SECONDS.between(start, Instant.now());

        if (seconds != 0) {
            crawlspeed = String.valueOf(crawled / seconds);
            analysisSpeed = String.valueOf(analysed / seconds);
        }

        if (crawled != 0) {
            progress = String.valueOf(analysed * 100 / crawled);

            seconds = (crawled - analysed) * seconds / analysed;
            estimate = String.format(
                    "%dH:%02dM:%02dS",
                    seconds / 3600,
                    (seconds % 3600) / 60,
                    seconds % 60);

        }
        log.info("Running {} / {} ({} %) - crawl speed: {}/s. - analysis speed: {}/s. - estimate to completion: {}",
                analysed,
                crawled,
                progress,
                crawlspeed,
                analysisSpeed,
                estimate);

    }

    @Override
    public void onClose() {
        log.debug("Progress log closed");
    }


}
