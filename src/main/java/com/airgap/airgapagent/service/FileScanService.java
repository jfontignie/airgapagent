package com.airgap.airgapagent.service;

import com.airgap.airgapagent.algo.Automaton;
import com.airgap.airgapagent.domain.ExactMatchContext;
import com.airgap.airgapagent.domain.ExactMatchingResult;
import com.airgap.airgapagent.utils.CsvWriter;
import com.airgap.airgapagent.utils.FileWalkerContext;
import com.airgap.airgapagent.utils.PersistentFileWalker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.scheduler.Schedulers;

import java.io.FileWriter;
import java.io.IOException;
import java.util.StringJoiner;
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
            try (FileWriter errorWriter = new FileWriter(exactMatchContext.getErrorFile())) {

                Automaton automaton = ahoCorasickMatcherService.buildAutomaton(
                        corpusBuilderService.buildSet(exactMatchContext.getExactMatchFile()), false);

                PersistentFileWalker persistentFileWalker = new PersistentFileWalker(fileWalkerService,
                        exactMatchContext.getStateFile(),
                        exactMatchContext.getSaveInterval());

                persistentFileWalker.listFiles(context)
                        .parallel()
                        .runOn(Schedulers.parallel())
                        .map(file -> contentReaderService.getContent(file).map(r -> {
                            AtomicInteger countFound = new AtomicInteger();
                            try {
                                ahoCorasickMatcherService.listMatches(r, automaton)
                                        .take(exactMatchContext.getMaxHit())
                                        .subscribe(matchingResult -> countFound.incrementAndGet())
                                        .dispose();
                            } catch (Exception e) {
                                //catch possible reader exceptions but do nothing
                                log.error("Error while reading {} - {}", file, e.getMessage());
                                try {
                                    errorWriter.write(
                                            new StringJoiner(";", "", "\n")
                                                    .add(file.toString())
                                                    .add(e.toString())
                                                    .toString());
                                    errorWriter.flush();
                                } catch (IOException ioException) {
                                    log.error("Error wile saving data");
                                }
                            }
                            return new ExactMatchingResult(file, countFound.get());
                        }))
                        .filter(result -> result.map(r -> r.getOccurrences() >= exactMatchContext.getMinHit()).orElse(false))
                        .doOnNext(result -> result.ifPresent(r -> {
                                    log.info("Found {}", r);
                                    foundWriter.save(r);
                                }
                        ))
                        .sequential()
                        .blockLast();
            }
        }
    }

}
