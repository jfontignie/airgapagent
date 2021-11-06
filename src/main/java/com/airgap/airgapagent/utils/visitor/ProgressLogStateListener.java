package com.airgap.airgapagent.utils.visitor;

import com.airgap.airgapagent.utils.CrawlState;
import com.airgap.airgapagent.utils.IntervalRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

/**
 * com.airgap.airgapagent.service
 * Created by Jacques Fontignie on 5/30/2020.
 */
public class ProgressLogStateListener<T> extends SearchEventAdapter<T> {
    private static final Logger log = LoggerFactory.getLogger(ProgressLogStateListener.class);
    private final IntervalRunner runner;

    private Instant start;
    private String crawlSpeed = "n/a";
    private String analysisSpeed = "n/a";

    public ProgressLogStateListener(long interval) {
        runner = IntervalRunner.of(Duration.ofSeconds(interval), true);
    }

    @Override
    public void onInit(CrawlState<T> crawlState) {
        start = Instant.now();
        log.info("Initializing search engine");
    }


    @Override
    public void onVisited(CrawlState<T> crawlState, T object) {
        runner.trigger(t -> logProgress(crawlState));
    }

    private void logProgress(CrawlState<T> crawlState) {
        int crawled = crawlState.getCrawled();
        int analysed = crawlState.getVisited();
        String progress = "n/a";
        String estimate = "n/a";

        long seconds = ChronoUnit.SECONDS.between(start, Instant.now());

        if (seconds != 0) {
            crawlSpeed = String.valueOf(crawled / seconds);
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
        log.info("Found {} elements - {} / {} ({} %) - crawl speed: {}/s. - analysis speed: {}/s. - estimate to completion: {}",
                crawlState.getFound(),
                analysed,
                crawled,
                progress,
                crawlSpeed,
                analysisSpeed,
                estimate);
    }

    @Override
    public void onClose(CrawlState<T> crawlState) {
        log.info("Scan finished. Found {} elements - crawl speed: {}/s. - analysis speed: {}/s.",
                crawlState.getFound(),
                crawlSpeed,
                analysisSpeed);
    }


}
