package com.airgap.airgapagent.algo;

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
class AhoCorasickMatcherTest {

    @Test
    void match() throws IOException {
        CorpusBuilderService service = new CorpusBuilderService();
        Set<String> set = service.buildSet(ConstantsTest.CORPUS_SAMPLE);
        AhoCorasickMatcher matcher = new AhoCorasickMatcher();
        String s = "603046751.7603046751.7,523650288.4";
        List<MatchingResult> found = new ArrayList<>();
        char[][] keywords =
                set.stream().map(String::toCharArray).collect(Collectors.toList()).toArray(new char[0][0]);
        matcher.match(new StringReader(s),
                found::add, keywords
        );
        Assertions.assertEquals(3, found.size());
        System.out.println(found);

    }

    @Test
    void testFiles() throws IOException {
        CorpusBuilderService service = new CorpusBuilderService();
        Set<String> set = service.buildSet(ConstantsTest.CORPUS_SAMPLE);
        AhoCorasickMatcher matcher = new AhoCorasickMatcher();
        Automaton automaton = new Automaton(Collections.singleton(AutomatonOption.CASE_INSENSITIVE), set);
        AtomicInteger count = new AtomicInteger();
        matcher.match(new FileReader("src/test/resources/sample/sample.csv"),
                matchingResult -> count.incrementAndGet(),
                automaton);
        Assertions.assertTrue(count.get() > 0);

    }


}
