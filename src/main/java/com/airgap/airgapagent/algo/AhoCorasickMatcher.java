package com.airgap.airgapagent.algo;

import java.io.IOException;
import java.io.Reader;
import java.util.function.Consumer;

/**
 * com.airgap.airgapagent.algo
 * Created by Jacques Fontignie on 5/29/2020.
 * <p>
 * https://codereview.stackexchange.com/questions/115624/aho-corasick-for-multiple-exact-string-matching-in-java
 */
public class AhoCorasickMatcher extends MultipleStringMatcher {

    public void match(Reader reader, Consumer<MatchingResult> target, Automaton automaton) throws IOException {
        TrieNode state = automaton.getRoot();

        int textIndex = 0;
        while (true) {
            int current = reader.read();
            if (current == -1) {
                break;
            }
            char car = (char) current;

            while (state.getChild(car) == null) {
                state = automaton.getFail().get(state);
            }

            state = state.getChild(car);
            for (final int patternIndex : automaton.getPatterns().get(state)) {

                target.accept(new MatchingResult(textIndex + 1, automaton.getPattern(patternIndex)));
            }
            textIndex++;
        }
    }

    public void match(Reader reader, final Consumer<MatchingResult> target, char[]... patterns) throws IOException {
        if (patterns.length > 0) {
            Automaton automaton = new Automaton(patterns);
            match(reader, target, automaton);

        }

    }


}
