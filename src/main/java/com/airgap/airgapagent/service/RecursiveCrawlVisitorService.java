package com.airgap.airgapagent.service;

import com.airgap.airgapagent.service.crawl.CrawlService;
import com.airgap.airgapagent.utils.CrawlState;
import com.airgap.airgapagent.utils.filters.VisitorFilter;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

/**
 * com.airgap.airgapagent.service
 * Created by Jacques Fontignie on 5/23/2020.
 */
public class RecursiveCrawlVisitorService {

    public <T extends Comparable<T>> Flux<T> list(
            VisitorFilter<T> visitorFilter,
            CrawlService<T> crawlService,
            CrawlState<T> crawlState) {
        crawlState.init();
        return Flux.<T>create(fluxSink -> visit(visitorFilter, crawlService, crawlState,
                        fluxSink, crawlState.getRoot()))
                .doOnNext(crawlState::setReference);
    }

    private <T extends Comparable<T>> void visit(VisitorFilter<T> visitorFilter,
                                                 CrawlService<T> crawlService,
                                                 CrawlState<T> crawlState,
                                                 FluxSink<T> fluxSink,
                                                 T root) {
        recursiveVisit(visitorFilter, crawlService, fluxSink, crawlState, root);
        fluxSink.complete();
    }

    private <T extends Comparable<T>> void recursiveVisit(VisitorFilter<T> visitorFilter,
                                                          CrawlService<T> crawlService,
                                                          FluxSink<T> fluxSink,
                                                          CrawlState<T> crawlState,
                                                          T current) {
        //If the last file to visit is bigger than the file, it means we can restart where we left
        if (!visitorFilter.accept(current)) {
            return;
        }
        boolean leaf = crawlService.isLeaf(current);
        if (crawlState.getOriginal() != null && leaf && crawlState.getOriginal().compareTo(current) > 0) {
            return;
        }
        if (leaf) {
            crawlState.incCrawled();
            fluxSink.next(current);
        } else {
            crawlService.listChildren(current).stream()
                    .sorted()
                    .forEach(t -> recursiveVisit(visitorFilter, crawlService, fluxSink, crawlState, t));
        }
    }

}
