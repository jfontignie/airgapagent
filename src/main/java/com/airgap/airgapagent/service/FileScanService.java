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

import java.io.File;
import java.io.IOException;
import java.util.Set;

/**
 * com.airgap.airgapagent.service
 * Created by Jacques Fontignie on 5/30/2020.
 */
@Service
public class FileScanService {

    private static final Logger log = LoggerFactory.getLogger(FileScanService.class);

    private final CorpusBuilderService corpusBuilderService;
    private final VisitorService visitorService;
    private final ErrorService errorService;

    public FileScanService(
            VisitorService visitorService,
            CorpusBuilderService corpusBuilderService,
            ErrorService errorService) {
        this.corpusBuilderService = corpusBuilderService;
        this.visitorService = visitorService;
        this.errorService = errorService;
    }

    public <T extends Comparable<T>> void run(ExactMatchContext exactMatchContext,
                                              CrawlService<T> crawlService,
                                              StateConverter<T> stateConverter,
                                              WalkerContext<T> context,
                                              AhoCorasickMatcherService ahoCorasickMatcherService) throws IOException {

        try (CsvWriter foundWriter = new CsvWriter(exactMatchContext.getFoundFile())) {
            try (PersistentStateVisitor<T> persistentStateVisitor = new PersistentStateVisitor<>(
                    exactMatchContext.getStateFile(),
                    exactMatchContext.getSaveInterval(),
                    context,
                    stateConverter
            )) {
                Automaton automaton = ahoCorasickMatcherService.buildAutomaton(
                        corpusBuilderService.buildSet(exactMatchContext.getExactMatchFile()), Set.of(AutomatonOption.CASE_INSENSITIVE));


                persistentStateVisitor.init();


                visitorService.list(crawlService, context)
                        //Persist the state
                        .doOnNext(file -> persistentStateVisitor.persist())

                        //Run on parallel
                        .parallel()
                        .runOn(Schedulers.parallel())

                        //Init the reader and forget if empty
                        .flatMap(file -> crawlService.getContentReader(file)
                                .map(reader -> Flux.just(new DataReader<>(file, reader)))
                                .orElse(Flux.empty()))

                        //Parse the data to find keywords

                        .flatMap(dataReader ->
                                ahoCorasickMatcherService.listMatches(dataReader.getReader(), automaton)
                                        .doOnError(throwable -> errorService.error(dataReader.getSource(),
                                                "Impossible to parse file",
                                                throwable))
                                        .take(exactMatchContext.getMaxHit())
                                        .count()
                                        .onErrorReturn(0L)
                                        .map(count -> new ExactMatchingResult<>(dataReader.getSource(), Math.toIntExact(count)))
                                        .flux())

                        //Filter for the one with enough occurences found
                        .filter(result -> result.getOccurrences() >= exactMatchContext.getMinHit())

                        //Display the result
                        .doOnNext(exactMatchingResult -> {
                                    log.info("Found {}", exactMatchingResult);
                                    foundWriter.save(exactMatchingResult, stateConverter);
                                }
                        )

                        //Go back on sequential mode
                        .sequential()

                        //Wait the last one has been handler
                        .blockLast();

            }
        }

    }

    public void run(ExactMatchContext exactMatchContext, FileService crawlService) throws IOException {


        AhoCorasickMatcherService ahoCorasickMatcherService = new AhoCorasickMatcherService();

        WalkerContext<File> context = WalkerContext.of(exactMatchContext.getScanFolder());

        StateConverter<File> stateConverter = FileStateConverter.of();

        run(
                exactMatchContext,
                crawlService,
                stateConverter,
                context,
                ahoCorasickMatcherService
        );
    }
}
