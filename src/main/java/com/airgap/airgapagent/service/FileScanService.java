package com.airgap.airgapagent.service;

import com.airgap.airgapagent.algo.Automaton;
import com.airgap.airgapagent.domain.ExactMatchContext;
import com.airgap.airgapagent.domain.ExactMatchingResult;
import com.airgap.airgapagent.utils.CsvWriter;
import com.airgap.airgapagent.utils.ErrorWriter;
import com.airgap.airgapagent.utils.FileWalkerContext;
import com.airgap.airgapagent.utils.PersistentFileWalker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.scheduler.Schedulers;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * com.airgap.airgapagent.service
 * Created by Jacques Fontignie on 5/30/2020.
 */
@Service
public class FileScanService {

    private static final Logger log = LoggerFactory.getLogger(FileScanService.class);

    private final CorpusBuilderService corpusBuilderService;
    private final ContentReaderService contentReaderService;
    private final FileWalkerService fileWalkerService;

    public FileScanService(
            FileWalkerService fileWalkerService,
            CorpusBuilderService corpusBuilderService,
            ContentReaderService contentReaderService) {
        this.corpusBuilderService = corpusBuilderService;
        this.contentReaderService = contentReaderService;
        this.fileWalkerService = fileWalkerService;
    }

    public void run(ExactMatchContext exactMatchContext) throws IOException {

        AhoCorasickMatcherService ahoCorasickMatcherService = new AhoCorasickMatcherService();

        FileWalkerContext context = FileWalkerContext.of(exactMatchContext.getScanFolder());

        try (CsvWriter foundWriter = new CsvWriter(exactMatchContext.getFoundFile())) {
            try (ErrorWriter errorWriter = new ErrorWriter(exactMatchContext.getErrorFile())) {

                Automaton automaton = ahoCorasickMatcherService.buildAutomaton(
                        corpusBuilderService.buildSet(exactMatchContext.getExactMatchFile()), false);

                PersistentFileWalker persistentFileWalker = new PersistentFileWalker(fileWalkerService,
                        exactMatchContext.getStateFile(),
                        exactMatchContext.getSaveInterval());

                persistentFileWalker.listFiles(context)
                        .parallel()
                        .runOn(Schedulers.parallel())
                        .map(file -> contentReaderService.getContent(file).map(reader -> {
                            AtomicInteger countFound = new AtomicInteger();
                            ahoCorasickMatcherService.listMatches(reader, automaton)
                                    .take(exactMatchContext.getMaxHit())
                                    .doOnError(throwable -> errorWriter.trigger(file.toString(), throwable))
                                    .doOnNext(matchingResult -> countFound.incrementAndGet())
                                    .subscribe();
                            return new ExactMatchingResult(file.toString(), countFound.get());
                        }))
                        .filter(result -> result.map(exactMatchingResult -> exactMatchingResult.getOccurrences() >= exactMatchContext.getMinHit()).orElse(false))
                        .doOnNext(result -> result.ifPresent(exactMatchingResult -> {
                                    log.info("Found {}", exactMatchingResult);
                                    foundWriter.save(exactMatchingResult);
                                }
                        ))
                        .sequential()
                        .blockLast();
            }
        }
    }

}
