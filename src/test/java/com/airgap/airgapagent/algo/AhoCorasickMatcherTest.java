package com.airgap.airgapagent.algo;

import com.airgap.airgapagent.service.ExactMatchBuilderService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * com.airgap.airgapagent.algo
 * Created by Jacques Fontignie on 5/29/2020.
 */
class AhoCorasickMatcherTest {

    @Test
    void match() throws IOException {
        ExactMatchBuilderService service = new ExactMatchBuilderService();
        Set<String> set = service.buildSet(new File("src/test/resources/sample/bigsample.csv"));
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


}
