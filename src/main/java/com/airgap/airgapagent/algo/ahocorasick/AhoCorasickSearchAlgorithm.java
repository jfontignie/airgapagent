package com.airgap.airgapagent.algo.ahocorasick;

import com.airgap.airgapagent.algo.SearchAlgorithm;
import com.airgap.airgapagent.algo.SearchOption;
import com.airgap.airgapagent.algo.SearchResult;

import java.io.IOException;
import java.io.Reader;
import java.util.function.Consumer;

/**
 * com.airgap.airgapagent.algo
 * Created by Jacques Fontignie on 5/29/2020.
 * <p>
 * https://codereview.stackexchange.com/questions/115624/aho-corasick-for-multiple-exact-string-matching-in-java
 */
public class AhoCorasickSearchAlgorithm implements SearchAlgorithm {

    private final Automaton automaton;

    public AhoCorasickSearchAlgorithm(Automaton automaton) {
        this.automaton = automaton;
    }

    @Override
    public void match(Reader reader, Consumer<SearchResult> target) throws IOException {
        TrieNode state = automaton.getRoot();
        boolean isCaseInsensitive = automaton.getOptions().contains(SearchOption.CASE_INSENSITIVE);

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
                target.accept(new SearchResult(textIndex + 1, automaton.getPattern(patternIndex)));
            }
            textIndex++;
        }
    }


}
