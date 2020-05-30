package com.airgap.airgapagent.soar;

import com.airgap.airgapagent.algo.Automaton;
import com.airgap.airgapagent.service.*;
import org.apache.tika.Tika;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.scheduler.Schedulers;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.time.Duration;
import java.util.Optional;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Pattern;

/**
 * com.airgap.airgapagent.soar
 * Created by Jacques Fontignie( on 5/22/2020.
 */
class SoarSample {

    private static final Logger log = LoggerFactory.getLogger(SoarSample.class);
    //    private static final File SOURCE = new File("c:/");
//    private SyslogService syslogService;
//    AtomicInteger counter = new AtomicInteger();
//    AtomicLong last = new AtomicLong(System.currentTimeMillis());

    @Disabled("Verify performance")
    @Test
    void testCrawl() throws IOException {
        FileWalkerService service = new FileWalkerService();
        FileWalkerContext context = FileWalkerContext.of("c:/projects");
        AtomicInteger counter = new AtomicInteger();
        AtomicInteger current = new AtomicInteger();
        AtomicLong sizeProcessed = new AtomicLong();

        AhoCorasickMatcherService ahoCorasickMatcherService = new AhoCorasickMatcherService();
        ExactMatchBuilderService exactMatchBuilderService = new ExactMatchBuilderService();
        Automaton automaton = ahoCorasickMatcherService.buildAutomaton(
                exactMatchBuilderService.buildSet(new File("src/test/resources/sample/bigsample.csv")), false);

        ContentReaderService contentReaderService = new ContentReaderService();

        IntervalRunner runner = IntervalRunner.of(Duration.ofSeconds(10), true);

        service.listFiles(context)
                .doOnEach(f -> {
                    File file = f.get();
                    if (file != null) {
                        sizeProcessed.addAndGet(file.length());
                    }
                    current.incrementAndGet();
                    runner.trigger(() -> log.info("Running {} / {} ({} %) - Processed: {} M",
                            current,
                            context.getVisited(),
                            current.get() * 100 / context.getVisited(),
                            sizeProcessed.get() / 1024));
                })
                .parallel()
                .runOn(Schedulers.parallel())
                .filter(file -> contentReaderService.getContent(file).map(r -> {
                    AtomicInteger countFound = new AtomicInteger();
                    try {
                        ahoCorasickMatcherService.listMatches(r, automaton)
                                .subscribe(matchingResult -> countFound.incrementAndGet())
                                .dispose();
                    } catch (Exception e) {
                        return countFound.get() > 0;
                    }
                    return countFound.get() > 0;
                }).orElse(false))
                .doOnNext(f -> {
                    log.info("Found {} - {}, ", counter.get(), f);
                    counter.incrementAndGet();
                }).sequential().blockLast();

        Assertions.assertTrue(true);
    }

//    @Disabled
//    @Test
//    public void simple() throws InterruptedException {
//        SuffixFileFilter suffix = new SuffixFileFilter(Arrays.asList(".p12", ".crt"), IOCase.INSENSITIVE);
//
//        FileWalkerService fileWalkerService = new FileWalkerService();
//
//        FileWalkerContext walkerQuery = new FileWalkerContext(SOURCE);
//        Disposable disposable = fileWalkerService.listFiles(walkerQuery)
//                .doOnEach(f -> {
//                    counter.incrementAndGet();
//                    if (System.currentTimeMillis() - last.get() > 1000) {
//                        last.set(System.currentTimeMillis());
//
//                    }
//                })
//                .filter(f -> !new SuffixFileFilter(Arrays.asList(".exe", ".dll"), IOCase.INSENSITIVE).accept(f))
//                .parallel()
//                .runOn(Schedulers.parallel())
//                .filter(file -> reader(file).map(this::matches).orElse(false))
//                .doOnEach(f -> System.out.println("found: " + f.get()))
//                .sequential()
//                .delaySubscription(Duration.ofMillis(10))
//                .subscribe();
//
//
//        while (!disposable.isDisposed()) {
//            Thread.sleep(1000);
//        }
////        disposable.dispose();
//    }

    private boolean matches(Reader reader) {
        Scanner scanner = new Scanner(reader);
        return scanner.findWithinHorizon(Pattern.compile("password="), 0) != null;
    }

    private Optional<Reader> reader(File file) {
        Tika tika = new Tika();
        try {
            return Optional.of(tika.parse(file));
        } catch (IOException e) {
            return Optional.empty();
        }
    }

//    @Test
//    public void flow1(Folder folder, Folder targetFolder) {
//        folder.listFiles(file -> {
//            file = file.store("flow", Map.of("timestamp", Instant.now()));
//
//           if (file.get("timestamp").compareTo(file.lastModified()) < 0 &&
//                   (file.hasExtension("docx","p12") || file.getContent().contains("pwd","password"))) {
//               file.copy(targetFolder,TIMESTAMP);
//               syslog("suspicious file",file);
//           }
//        });
//    }

//    @Test
//    public void flow2(File sourceFolder, File targetFolder) {
//        List<Disposable> disposables = new ArrayList<>();
//
//        fileService.listFiles(sourceFolder).doOnEach(file -> {
//            Optional<AirFile> previousFile = storeService.store("flow", file, Map.of("timestamp", Instant.now()));
//            if (previousFile.isPresent() &&
//                    storeService.get(previousFile.get(), "timestamp", Instant.MIN).compareTo(
//                            fileService.getLastModified(file)) < 0) {
//                if (fileService.hasExtension(file, "docx", "p12")) {
//                    fileService.copy(sourceFolder, file, targetFolder, FileCopy.TIMESTAMP);
//                    syslogService.syslog("suspicious file", file);
//                }
//                Disposable disposable = regexService.ifPresent(fileService.getContent(file), Arrays.asList("pwd", "password"), (match) -> {
//                    fileService.copy(sourceFolder, file, targetFolder, FileCopy.TIMESTAMP);
//                    syslogService.syslog("suspicious file", file);
//                });
//                disposables.add(disposable);
//            }
//        }).blockLast();
//        disposables.forEach(Disposable::dispose);
//        Assertions.assertTrue(true);
//    }

//    @Test
//    public void checkURL(Map<URL, boolean> map){
//        map.entrySet().stream().forEach(e -> {
//            e.getKey().connect(result -> {
//                e.getKey().store("check", Map.of("timestamp", Instant.now(), "succesc", result));
//                if (result != e.getValue()) {
//                    syslog("Invalid connectivity",e.getKey());
//                }
//            }
//        });
//    }
//
//    @Test
//    public void mailCheck(Mailbox mailbox){
//        mailbox.remove(mail -> {
//            sandbox.scan(mail).whenSuspicous(scan -> {
//                mail.extractURL(url -> url
//                        .scan()
//                        .whenSuspicious(urlScan -> askAnalyst(urlScan,"susppicous","clean").whenAnswer(0,url -> firewall.blackList(url))));
//            })
//        })
//    }
}
