package com.airgap.airgapagent.utils.visitor;

import com.airgap.airgapagent.utils.CrawlState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

/**
 * com.airgap.airgapagent.service
 * Created by Jacques Fontignie on 5/30/2020.
 */
public class ProgressLogStateVisitor<T> implements StateVisitor {
    private static final Logger log = LoggerFactory.getLogger(ProgressLogStateVisitor.class);

    private final CrawlState<T> crawlState;

    private Instant start;

    public ProgressLogStateVisitor(CrawlState<T> crawlState) {
        this.crawlState = crawlState;

    }

    public void init() {
        start = Instant.now();
    }

    @Override
    public void visit(int analysed) {

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
    public void close() {
        log.debug("Progress log closed");
    }


}