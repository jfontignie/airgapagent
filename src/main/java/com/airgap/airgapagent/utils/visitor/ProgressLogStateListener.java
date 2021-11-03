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
    private String crawlspeed = "n/a";
    private String analysisSpeed = "n/a";

    public ProgressLogStateListener(int interval) {
        super(interval);
    }

    @Override
    public void onInit() {
        start = Instant.now();
    }

    @Override
    public void onFoundEvent(CrawlState<T> crawlState, ExactMatchResult<T> notUsed) {
        int crawled = crawlState.getCrawled();
        int analysed = crawlState.getVisited();
        String progress = "n/a";
        String estimate = "n/a";

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
        log.info("{} ... Found {} elements - analysed/crawled {} / {} ({} %) - crawl speed: {}/s. - analysis speed: {}/s. - estimate to completion: {}",
                "Found so far.",
                crawlState.getFound(),
                analysed,
                crawled,
                progress,
                crawlspeed,
                analysisSpeed,
                estimate);

    }

    @Override
    public void onClose(CrawlState<T> crawlState) {
        log.info("Scan finished. Found {} elements - crawl speed: {}/s. - analysis speed: {}/s.",
                crawlState.getFound(),
                crawlspeed,
                analysisSpeed);
    }


}
