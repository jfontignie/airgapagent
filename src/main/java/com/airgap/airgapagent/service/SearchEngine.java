package com.airgap.airgapagent.service;

import com.airgap.airgapagent.domain.ExactMatchResult;
import com.airgap.airgapagent.utils.CrawlState;
import com.airgap.airgapagent.utils.visitor.SearchEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.Objects;

/**
 * com.airgap.airgapagent.service
 * Created by Jacques Fontignie on 5/30/2020.
 */
@Service
public class SearchEngine {

    private static final Logger log = LoggerFactory.getLogger(SearchEngine.class);

    private final RecursiveCrawlVisitorService recursiveCrawlVisitorService;
    private final MatcherService matcherService;

    public SearchEngine() {
        this.recursiveCrawlVisitorService = new RecursiveCrawlVisitorService();
        this.matcherService = new MatcherService();
    }

    @SuppressWarnings("java:S2095")
    public <T extends Comparable<T>> Flux<ExactMatchResult<T>> buildScan(SearchContext<T> searchContext) {

        CrawlState<T> state = searchContext.getCrawlState();
        List<SearchEventListener<T>> listeners = searchContext.getListeners();
        listeners.forEach(SearchEventListener::onInit);

        return recursiveCrawlVisitorService.list(searchContext.getVisitorFilter(), searchContext.getCrawlService(), state)
                //Run on parallel
                .parallel()
                .runOn(Schedulers.parallel())

                .flatMap(file -> {
                    state.incVisited();
                    listeners.forEach(l -> l.onVisited(state, file));

                    //Init the reader and forget if empty
                    return searchContext.getCrawlService().getContentReader(file)
                            .map(Flux::just)
                            .orElse(Flux.empty());
                })

                //Parse the data to find keywords
                .flatMap(dataReader ->
                        matcherService.listMatches(dataReader.getReader(), searchContext.getSearchAlgorithm())
                                .doOnError(throwable -> {
                                    state.incError();
                                    listeners.forEach(l -> l.onError(dataReader, throwable));
                                })
                                .count()
                                .onErrorReturn(0L)
                                .map(counter -> new ExactMatchResult<>(dataReader, Math.toIntExact(counter)))
                                .flux())

                //Filter for the one with enough occurrences found
                .filter(result -> result.getOccurrences() >= searchContext.getConfiguration().getMinHit())
                .doOnNext(result -> {
                    state.incFound();
                    listeners.forEach(l -> l.onFound(state, result));
                })
                .sequential()
                .doOnTerminate(() -> listeners.forEach(l -> l.onClose(state)));
    }

    public <T extends Comparable<T>> long scan(SearchContext<T> searchContext) {
        log.info("Initializing scan");

        Long count = buildScan(searchContext)
                .count()
                //Wait the last one has been handled
                .block();

        log.info("Operation finished. Files found: {}", count);
        return Objects.requireNonNullElse(count, 0L);
    }

}
