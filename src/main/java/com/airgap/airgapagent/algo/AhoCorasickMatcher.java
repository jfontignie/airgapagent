package com.airgap.airgapagent.algo;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.function.Consumer;

/**
 * com.airgap.airgapagent.algo
 * Created by Jacques Fontignie on 5/29/2020.
 * <p>
 * https://codereview.stackexchange.com/questions/115624/aho-corasick-for-multiple-exact-string-matching-in-java
 */
public class AhoCorasickMatcher {

    public void match(Reader reader, Consumer<MatchingResult> target, Automaton automaton) throws IOException {
        TrieNode state = automaton.getRoot();
        boolean isCaseInsensitive = automaton.getOptions().contains(AutomatonOption.CASE_INSENSITIVE);

        int textIndex = 0;
        while (true) {
            int current = reader.read();
            if (current == -1) {
                break;
            }

            char car = (char) (current & 0xFF);
            if (isCaseInsensitive) {
                car = Character.toLowerCase(car);
            }

            while (state.getChild(car) == null) {
                state = state.getFail();
            }

            state = state.getChild(car);
            for (final int patternIndex : state.getPatterns()) {
                target.accept(new MatchingResult(textIndex + 1, automaton.getPattern(patternIndex)));
            }
            textIndex++;
        }
    }


    public void match(String text, Consumer<MatchingResult> consumer, Automaton automaton) throws IOException {
        StringReader stringReader = new StringReader(text);
        match(stringReader, consumer, automaton);
    }
}
