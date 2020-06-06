package com.airgap.airgapagent.service;

import com.airgap.airgapagent.algo.Automaton;
import com.airgap.airgapagent.algo.AutomatonOption;
import com.airgap.airgapagent.domain.ExactMatchContext;
import com.airgap.airgapagent.domain.ExactMatchingResult;
import com.airgap.airgapagent.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.io.IOException;
import java.time.Duration;
import java.util.Objects;
import java.util.Set;

/**
 * com.airgap.airgapagent.service
 * Created by Jacques Fontignie on 5/30/2020.
 */
@Service
public class ExactMatchService {

    private static final Logger log = LoggerFactory.getLogger(ExactMatchService.class);

    private final CorpusBuilderService corpusBuilderService;
    private final VisitorService visitorService;
    private final ErrorService errorService;
    private final AhoCorasickMatcherService ahoCorasickMatcherService;

    public ExactMatchService(
            VisitorService visitorService,
            CorpusBuilderService corpusBuilderService,
            ErrorService errorService) {
        this.corpusBuilderService = corpusBuilderService;
        this.visitorService = visitorService;
        this.errorService = errorService;
        ahoCorasickMatcherService = new AhoCorasickMatcherService();

    }

    @SuppressWarnings("java:S2095")
    public <T extends Comparable<T>> Flux<ExactMatchingResult<T>> buildScan(ExactMatchContext<T> exactMatchContext,
                                                                            CrawlService<T> crawlService,
                                                                            StateConverter<T> stateConverter) throws IOException {


        //noinspection BlockingMethodInNonBlockingContext
        Automaton automaton = ahoCorasickMatcherService.buildAutomaton(
                corpusBuilderService.buildSet(exactMatchContext.getExactMatchFile()), Set.of(AutomatonOption.CASE_INSENSITIVE));


        WalkerContext<T> context = WalkerContext.of(exactMatchContext.getRoot());
        PersistentStateVisitor<T> persistentStateVisitor = new PersistentStateVisitor<>(
                exactMatchContext.getStateFile(),
                exactMatchContext.getSaveInterval(),
                context,
                stateConverter
        );
        persistentStateVisitor.init();


        return visitorService.list(crawlService, context)
                //Persist the state
                .doOnNext(file -> persistentStateVisitor.persist())

                //Run on parallel
                .parallel()
                .runOn(Schedulers.parallel())

                //Init the reader and forget if empty
                .flatMap(file -> {
                    try {
                        //noinspection BlockingMethodInNonBlockingContext
                        DataReader<T> contentReader = crawlService.getContentReader(file);
                        return Flux.just(contentReader);
                    } catch (IOException e) {
                        errorService.error(file, "Impossible to parse content", e);
                        return Flux.empty();
                    }
                })

                //Parse the data to find keywords

                .flatMap(dataReader ->
                        ahoCorasickMatcherService.listMatches(dataReader.getReader(), automaton)
                                .doOnError(throwable -> errorService.error(dataReader.getSource(),
                                        "Impossible to parse file",
                                        throwable))
                                .count()
                                .onErrorReturn(0L)
                                .map(counter -> new ExactMatchingResult<>(dataReader, Math.toIntExact(counter)))
                                .flux())

                //Filter for the one with enough occurences found
                .filter(result -> result.getOccurrences() >= exactMatchContext.getMinHit())
                .sequential().doOnTerminate(persistentStateVisitor::close);
    }


    public <T extends Comparable<T>> long scan(ExactMatchContext<T> exactMatchContext,
                                               CrawlService<T> crawlService,
                                               StateConverter<T> stateConverter) throws IOException {

        try (CsvWriter dataWriter = new CsvWriter(exactMatchContext.getFoundFile())) {

            IntervalRunner runner = IntervalRunner.of(Duration.ofSeconds(5), true);

            Long count = buildScan(exactMatchContext,
                    crawlService,
                    stateConverter)
                    //Display the result
                    .doOnNext(exactMatchingResult -> dataWriter.save(exactMatchingResult, stateConverter))
                    .doOnNext(tExactMatchingResult -> runner.trigger(integer -> log.info("Elements found so far: {}", integer)))
                    .count()
                    //Wait the last one has been handler
                    .block();

            log.info("Operation finished. Files found: {}", count);
            return Objects.requireNonNullElse(count, 0L);
        }
    }


}
