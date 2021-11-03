package com.airgap.airgapagent.algo;

import com.airgap.airgapagent.algo.ahocorasick.AhoCorasickSearchAlgorithm;
import com.airgap.airgapagent.algo.ahocorasick.Automaton;
import com.airgap.airgapagent.service.CorpusBuilderService;
import com.airgap.airgapagent.utils.ConstantsTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * com.airgap.airgapagent.algo
 * Created by Jacques Fontignie on 5/29/2020.
 */
class AhoCorasickSearchAlgorithmTest {

    @Test
    void match() throws IOException {
        CorpusBuilderService service = new CorpusBuilderService();
        Set<String> set = service.buildSet(ConstantsTest.CORPUS_SAMPLE);
        String s = "603046751.7603046751.7,523650288.4";
        List<SearchResult> found = new ArrayList<>();
        char[][] keywords =
                set.stream().map(String::toCharArray).collect(Collectors.toList()).toArray(new char[0][0]);
        Automaton automaton = new Automaton(Collections.singleton(SearchOption.CASE_INSENSITIVE), set);
        SearchAlgorithm searchAlgorithm = new AhoCorasickSearchAlgorithm(automaton);

        searchAlgorithm.match(new StringReader(s),
                found::add);
        Assertions.assertEquals(3, found.size());
        System.out.println(found);

    }

    @Test
    void testFiles() throws IOException {
        CorpusBuilderService service = new CorpusBuilderService();
        Set<String> set = service.buildSet(ConstantsTest.CORPUS_SAMPLE);
        Automaton automaton = new Automaton(Collections.singleton(SearchOption.CASE_INSENSITIVE), set);
        SearchAlgorithm searchAlgorithm = new AhoCorasickSearchAlgorithm(automaton);

        AtomicInteger count = new AtomicInteger();
        searchAlgorithm.match(new FileReader("src/test/resources/sample/sample.csv"),
                matchingResult -> count.incrementAndGet());
        Assertions.assertTrue(count.get() > 0);

    }


}
