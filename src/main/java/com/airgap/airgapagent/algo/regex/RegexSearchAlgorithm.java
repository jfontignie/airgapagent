package com.airgap.airgapagent.algo.regex;

import com.airgap.airgapagent.algo.SearchAlgorithm;
import com.airgap.airgapagent.algo.SearchResult;

import java.io.Reader;
import java.util.Scanner;
import java.util.Set;
import java.util.function.Consumer;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * com.airgap.airgapagent.algo.regex
 * Created by Jacques Fontignie on 6/14/2020.
 */
public class RegexSearchAlgorithm implements SearchAlgorithm {

    private final Pattern regex;

    public RegexSearchAlgorithm(Set<String> regexes) {
        regex = Pattern.compile(
                regexes.stream().map(r -> "(" + r + ")").collect(Collectors.joining("|")));
    }

    @Override
    public void match(Reader reader, Consumer<SearchResult> consumer) {
        Scanner scanner = new Scanner(reader);
        while (scanner.hasNext()) {
            String found = scanner.findWithinHorizon(regex, 0);
            if (found == null) {
                return;
            } else {
                consumer.accept(new SearchResult(0, found.toCharArray()));
            }

        }
    }


}
