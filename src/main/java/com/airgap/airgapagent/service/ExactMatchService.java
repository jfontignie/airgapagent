package com.airgap.airgapagent.service;

import com.airgap.airgapagent.algo.AlgoFactory;
import com.airgap.airgapagent.algo.MatchOption;
import com.airgap.airgapagent.algo.Matcher;
import com.airgap.airgapagent.configuration.AbstractScanConfiguration;
import com.airgap.airgapagent.configuration.AbstractSearchConfiguration;
import com.airgap.airgapagent.domain.ExactMatchingResult;
import com.airgap.airgapagent.service.crawl.CrawlService;
import com.airgap.airgapagent.service.syslog.SyslogFormatter;
import com.airgap.airgapagent.service.syslog.SyslogService;
import com.airgap.airgapagent.utils.CsvWriter;
import com.airgap.airgapagent.utils.IntervalRunner;
import com.airgap.airgapagent.utils.StateConverter;
import com.airgap.airgapagent.utils.WalkerContext;
import com.airgap.airgapagent.utils.visitor.PersistentStateVisitor;
import com.airgap.airgapagent.utils.visitor.ProgressLogStateVisitor;
import com.airgap.airgapagent.utils.visitor.ScheduledStateVisitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.ParallelFlux;
import reactor.core.scheduler.Schedulers;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Map;
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
    private final SyslogService syslogService;
    private final MatcherService matcherService;

    public ExactMatchService(
            VisitorService visitorService,
            CorpusBuilderService corpusBuilderService,
            ErrorService errorService,
            SyslogService syslogService,
            MatcherService matcherService) {
        this.corpusBuilderService = corpusBuilderService;
        this.visitorService = visitorService;
        this.errorService = errorService;
        this.syslogService = syslogService;
        this.matcherService = matcherService;
    }

    @SuppressWarnings("java:S2095")
    public <T extends Comparable<T>> Flux<ExactMatchingResult<T>> buildScan(AbstractScanConfiguration<T> exactMatchContext,
                                                                            CrawlService<T> crawlService,
                                                                            StateConverter<T> stateConverter) throws IOException {


        //noinspection BlockingMethodInNonBlockingContext
        Matcher matcher = AlgoFactory.getMatcher(exactMatchContext.getAlgo(),
                Set.of(MatchOption.CASE_INSENSITIVE),
                corpusBuilderService.buildSet(exactMatchContext.getCorpusLocation()));

        WalkerContext<T> context = WalkerContext.of(exactMatchContext.getRootLocation());
        ScheduledStateVisitor scheduledStateVisitor = new ScheduledStateVisitor(
                exactMatchContext.getSaveInterval(),
                List.of(
                        new ProgressLogStateVisitor<>(context),
                        new PersistentStateVisitor<>(exactMatchContext.getStateLocation(), context, stateConverter)
                )
        );
        scheduledStateVisitor.init();

        ParallelFlux<ExactMatchingResult<T>> flux = visitorService.list(crawlService, context)
                //Persist the state
                .doOnNext(file -> scheduledStateVisitor.visit())

                //Run on parallel
                .parallel()
                .runOn(Schedulers.parallel())

                //Init the reader and forget if empty
                .flatMap(file -> crawlService.getContentReader(file)
                        .map(Flux::just)
                        .orElse(Flux.empty()))

                //Parse the data to find keywords

                .flatMap(dataReader ->
                        matcherService.listMatches(dataReader.getReader(), matcher)
                                .doOnError(throwable -> errorService.error(dataReader.getSource(),
                                        "Impossible to parse file",
                                        throwable))
                                .count()
                                .onErrorReturn(0L)
                                .map(counter -> new ExactMatchingResult<>(dataReader, Math.toIntExact(counter)))
                                .flux())

                //Filter for the one with enough occurences found
                .filter(result -> result.getOccurrences() >= exactMatchContext.getMinHit());

        if (exactMatchContext.isSyslog()) {
            flux = flux.doOnNext(ExactMatchService.this::sendSyslog);
        }

        return flux.sequential().doOnTerminate(scheduledStateVisitor::close);
    }


    public <T extends Comparable<T>> long scan(AbstractSearchConfiguration<T> exactMatchContext,
                                               CrawlService<T> crawlService,
                                               StateConverter<T> stateConverter) throws IOException {

        try (CsvWriter dataWriter = new CsvWriter(exactMatchContext.getFoundLocation(), exactMatchContext.getExclude())) {

            IntervalRunner runner = IntervalRunner.of(Duration.ofSeconds(5), true);


            Long count = buildScan(exactMatchContext,
                    crawlService,
                    stateConverter)
                    //Display the result
                    .doOnNext(exactMatchingResult -> dataWriter.save(exactMatchingResult, stateConverter))
                    .doOnNext(exactMatchingResult -> runner.trigger(integer -> log.info("Elements found so far: {}", integer)))
                    .count()
                    //Wait the last one has been handler
                    .block();

            log.info("Operation finished. Files found: {}", count);
            return Objects.requireNonNullElse(count, 0L);
        }
    }

    private <T extends Comparable<T>> void sendSyslog(ExactMatchingResult<T> exactMatchingResult) {

        SyslogFormatter formatter = new SyslogFormatter(Map.of(
                "source", exactMatchingResult.getDataSource().getSource().toString(),
                "occurrences", String.valueOf(exactMatchingResult.getOccurrences())
        ));
        formatter.add(exactMatchingResult.getDataSource().getMetadata());

        try {
            syslogService.send(formatter.toString());
        } catch (IOException e) {
            errorService.error(exactMatchingResult.getDataSource().getSource(), "Impossible to send syslog", e);
        }
    }


}
