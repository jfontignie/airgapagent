package com.airgap.airgapagent.algo.regex;

import com.airgap.airgapagent.algo.Matcher;
import com.airgap.airgapagent.algo.MatchingResult;

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
public class RegexMatcher implements Matcher {

    private final Pattern regex;

    public RegexMatcher(Set<String> regexes) {
        regex = Pattern.compile(
                regexes.stream().map(r -> "(" + r + ")").collect(Collectors.joining("|")));
    }

    @Override
    public void match(Reader reader, Consumer<MatchingResult> consumer) {
        Scanner scanner = new Scanner(reader);
        while (scanner.hasNext()) {
            String found = scanner.findWithinHorizon(regex, 0);
            if (found == null) {
                return;
            } else {
                consumer.accept(new MatchingResult(0, found.toCharArray()));
            }

        }
    }


}
