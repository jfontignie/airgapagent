package com.airgap.airgapagent.service;

import com.airgap.airgapagent.algo.Automaton;
import com.airgap.airgapagent.algo.MatchingResult;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
        Automaton automaton = ahoCorasickMatcherService.buildAutomaton(set);

        List<MatchingResult> found = new ArrayList<>();
        Flux<MatchingResult> flux = ahoCorasickMatcherService.listMatches(new StringReader("603046751.7603046751.7,523650288.4"), automaton);
        flux.subscribe(found::add).dispose();

        Assertions.assertEquals(3, found.size());
        System.out.println(found);
    }
}
