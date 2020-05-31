package com.airgap.airgapagent.service;

import com.airgap.airgapagent.utils.WalkerContext;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

/**
 * com.airgap.airgapagent.service
 * Created by Jacques Fontignie on 5/23/2020.
 */
@Service
public class VisitorService {

    public <T extends Comparable<T>> Flux<T> list(CrawlService<T> crawlService,
                                                  WalkerContext<T> walkerContext) {
        T lastFileToVisit = walkerContext.getReference();

        return Flux.<T>create(fluxSink -> visit(crawlService, walkerContext, lastFileToVisit, fluxSink, walkerContext.getRoot()))
                .doOnNext(walkerContext::setReference);
    }

    private <T extends Comparable<T>> void visit(CrawlService<T> crawlService, WalkerContext<T> walkerContext, T reference, FluxSink<T> fluxSink, T root) {
        recursiveVisit(crawlService, fluxSink, walkerContext, root, reference);
        fluxSink.complete();
    }

    private <T extends Comparable<T>> void recursiveVisit(CrawlService<T> crawlService,
                                                          FluxSink<T> fluxSink,
                                                          WalkerContext<T> walkerState,
                                                          T current,
                                                          T reference) {
        //If the last file to visit is bigger than the file, it means we can restart where we left
        boolean leaf = crawlService.isLeaf(current);
        if (reference != null && leaf && reference.compareTo(current) > 0) {
            return;
        }
        if (leaf) {
            walkerState.incVisited();
            fluxSink.next(current);
        } else {
            crawlService.listChildren(current).stream()
                    .sorted()
                    .forEach(t -> recursiveVisit(crawlService, fluxSink, walkerState, t, reference));
        }
    }

}
