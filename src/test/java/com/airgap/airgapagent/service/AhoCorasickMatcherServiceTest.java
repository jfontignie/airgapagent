package com.airgap.airgapagent.service;

import com.airgap.airgapagent.algo.Automaton;
import com.airgap.airgapagent.algo.AutomatonOption;
import com.airgap.airgapagent.algo.MatchingResult;
import com.airgap.airgapagent.utils.ConstantsTest;
import org.apache.tika.Tika;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * com.airgap.airgapagent.service
 * Created by Jacques Fontignie on 5/29/2020.
 */
class AhoCorasickMatcherServiceTest {

    private static final Logger log = LoggerFactory.getLogger(AhoCorasickMatcherServiceTest.class);

    @Test
    void listMatches() throws IOException {

        CorpusBuilderService corpusBuilderService = new CorpusBuilderService();
        Set<String> set = corpusBuilderService.buildSet(ConstantsTest.CORPUS_SAMPLE);
        AhoCorasickMatcherService ahoCorasickMatcherService = new AhoCorasickMatcherService();
        Automaton automaton = ahoCorasickMatcherService.buildAutomaton(set, Set.of(AutomatonOption.CASE_INSENSITIVE));

        List<MatchingResult> found = new ArrayList<>();
        Flux<MatchingResult> flux = ahoCorasickMatcherService.listMatches(new StringReader("603046751.7603046751.7,523650288.4"), automaton);
        flux.subscribe(found::add).dispose();

        Assertions.assertEquals(3, found.size());
        System.out.println(found);
    }

    @Test
    void testError() throws IOException {

        CorpusBuilderService corpusBuilderService = new CorpusBuilderService();
        Set<String> set = corpusBuilderService.buildSet(ConstantsTest.CORPUS_SAMPLE);
        AhoCorasickMatcherService ahoCorasickMatcherService = new AhoCorasickMatcherService();
        Automaton automaton = ahoCorasickMatcherService.buildAutomaton(set, Set.of());

        Reader reader = new ExceptionReader();

        Long found = ahoCorasickMatcherService.listMatches(reader, automaton)
                .doOnError(throwable -> log.error("e", throwable))

                .take(2)
                .count()
                .onErrorReturn(7L)
                .flux()
                .blockLast();

        Assertions.assertEquals(7, found);
    }


    @Test
    void checkCase() {

        Set<String> set = Set.of("Password");
        AhoCorasickMatcherService ahoCorasickMatcherService = new AhoCorasickMatcherService();
        Automaton automaton = ahoCorasickMatcherService.buildAutomaton(set, Set.of(AutomatonOption.CASE_INSENSITIVE));

        List<MatchingResult> found = new ArrayList<>();
        Flux<MatchingResult> flux = ahoCorasickMatcherService.listMatches(new StringReader("paSsword"), automaton);
        flux.subscribe(found::add).dispose();

        Assertions.assertEquals(1, found.size());
        System.out.println(found);
    }

    @Disabled("Compare performance between regex and Aho Corasick")
    @Test
    void perfTest() throws IOException {

        perfTestRegex();
        perfTestRegex2();
        perfTestAhoCorasick();
    }

    private void perfTestAhoCorasick() throws IOException {
        long start = System.currentTimeMillis();
        Set<String> set = Set.of("Password", "abcd");
        AhoCorasickMatcherService ahoCorasickMatcherService = new AhoCorasickMatcherService();
        Automaton automaton = ahoCorasickMatcherService.buildAutomaton(set, Set.of());

        List<MatchingResult> found = new ArrayList<>();

        Tika tika = new Tika();
        Reader reader = tika.parse(new File("src/test/resources/big/sample.eml"));
        Flux<MatchingResult> flux = ahoCorasickMatcherService.listMatches(reader, automaton);
        flux.subscribe(found::add).dispose();

        Assertions.assertEquals(1, found.size());
        System.out.println(found);
        System.out.println("Aho Corasick : " + (System.currentTimeMillis() - start) + " ms.");


    }


    void perfTestRegex() throws IOException {
        long start = System.currentTimeMillis();

        Tika tika = new Tika();
        Reader reader = tika.parse(new File("src/test/resources/big/sample.eml"));
        Scanner scanner = new Scanner(reader);
        scanner.findWithinHorizon(Pattern.compile("password"), 0);

        System.out.println("Regex with one: " + (System.currentTimeMillis() - start) + " ms.");
    }

    void perfTestRegex2() throws IOException {
        long start = System.currentTimeMillis();

        Tika tika = new Tika();
        Reader reader = tika.parse(new File("src/test/resources/big/sample.eml"));
        Scanner scanner = new Scanner(reader);
        scanner.findWithinHorizon(Pattern.compile("password|abcd"), 0);

        System.out.println("Regex with two: " + (System.currentTimeMillis() - start) + " ms.");
    }

    private static class ExceptionReader extends Reader {

        @Override
        public int read(char[] chars, int i, int i1) throws IOException {
            throw new IOException("error");
        }

        @Override
        public void close() throws IOException {
            throw new IOException("error");
        }
    }

}
