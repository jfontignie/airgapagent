package com.airgap.airgapagent.algo;

import com.airgap.airgapagent.algo.ahocorasick.AhoCorasickSearchAlgorithm;
import com.airgap.airgapagent.algo.ahocorasick.Automaton;
import com.airgap.airgapagent.algo.regex.RegexSearchAlgorithm;

import java.util.Set;

/**
 * com.airgap.airgapagent.algo
 * Created by Jacques Fontignie on 6/14/2020.
 */
public class SearchAlgorithmFactory {

    private SearchAlgorithmFactory() {
        //Nothing to do
    }

    public static SearchAlgorithm getMatcher(SearchAlgorithmType searchAlgorithmType, Set<SearchOption> options, Set<String> keywords) {
        switch (searchAlgorithmType) {
            case AHO_CORASICK:
                Automaton automaton = new Automaton(options, keywords);
                return new AhoCorasickSearchAlgorithm(automaton);
            case REGEX:
                return new RegexSearchAlgorithm(keywords);
            case EXPERIMENTAL:

            default:
                throw new IllegalArgumentException("Invalid type: " + searchAlgorithmType);
        }
    }
}
