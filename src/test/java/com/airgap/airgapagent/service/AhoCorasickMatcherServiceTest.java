package com.airgap.airgapagent.service;

import com.airgap.airgapagent.algo.Automaton;
import com.airgap.airgapagent.algo.MatchingResult;
import org.apache.tika.Tika;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
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

    @Test
    void listMatches() throws IOException {

        ExactMatchBuilderService exactMatchBuilderService = new ExactMatchBuilderService();
        Set<String> set = exactMatchBuilderService.buildSet(new File("src/test/resources/sample/bigsample.csv"));
        AhoCorasickMatcherService ahoCorasickMatcherService = new AhoCorasickMatcherService();
        Automaton automaton = ahoCorasickMatcherService.buildAutomaton(set, false);

        List<MatchingResult> found = new ArrayList<>();
        Flux<MatchingResult> flux = ahoCorasickMatcherService.listMatches(new StringReader("603046751.7603046751.7,523650288.4"), automaton);
        flux.subscribe(found::add).dispose();

        Assertions.assertEquals(3, found.size());
        System.out.println(found);
    }


    @Test
    void checkCase() {

        Set<String> set = Set.of("Password");
        AhoCorasickMatcherService ahoCorasickMatcherService = new AhoCorasickMatcherService();
        Automaton automaton = ahoCorasickMatcherService.buildAutomaton(set, false);

        List<MatchingResult> found = new ArrayList<>();
        Flux<MatchingResult> flux = ahoCorasickMatcherService.listMatches(new StringReader("paSsword"), automaton);
        flux.subscribe(found::add).dispose();

        Assertions.assertEquals(1, found.size());
        System.out.println(found);
    }

    @Disabled
    @Test
    public void perfTest() throws IOException {

        perfTestRegex();
        perfTestRegex2();
        perfTestAhoCorasick();
    }

    private void perfTestAhoCorasick() throws IOException {
        long start = System.currentTimeMillis();
        Set<String> set = Set.of("Password", "abcd");
        AhoCorasickMatcherService ahoCorasickMatcherService = new AhoCorasickMatcherService();
        Automaton automaton = ahoCorasickMatcherService.buildAutomaton(set, false);

        List<MatchingResult> found = new ArrayList<>();

        Tika tika = new Tika();
        Reader reader = tika.parse(new File("src/test/resources/big/sample.eml"));
        Flux<MatchingResult> flux = ahoCorasickMatcherService.listMatches(reader, automaton);
        flux.subscribe(found::add).dispose();

        Assertions.assertEquals(1, found.size());
        System.out.println(found);
        System.out.println("Aho Corasick : " + (System.currentTimeMillis() - start) + " ms.");


    }


    public void perfTestRegex() throws IOException {
        long start = System.currentTimeMillis();

        Tika tika = new Tika();
        Reader reader = tika.parse(new File("src/test/resources/big/sample.eml"));
        Scanner scanner = new Scanner(reader);
        scanner.findWithinHorizon(Pattern.compile("password"), 0);

        System.out.println("Regex with one: " + (System.currentTimeMillis() - start) + " ms.");
    }

    public void perfTestRegex2() throws IOException {
        long start = System.currentTimeMillis();

        Tika tika = new Tika();
        Reader reader = tika.parse(new File("src/test/resources/big/sample.eml"));
        Scanner scanner = new Scanner(reader);
        scanner.findWithinHorizon(Pattern.compile("password|abcd"), 0);

        System.out.println("Regex with two: " + (System.currentTimeMillis() - start) + " ms.");
    }
}
