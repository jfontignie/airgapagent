package com.airgap.airgapagent.algo;

import com.airgap.airgapagent.algo.ahocorasick.AhoCorasickMatcher;
import com.airgap.airgapagent.algo.ahocorasick.Automaton;
import com.airgap.airgapagent.algo.regex.RegexMatcher;

import java.util.Set;

/**
 * com.airgap.airgapagent.algo
 * Created by Jacques Fontignie on 6/14/2020.
 */
public class AlgoFactory {

    private AlgoFactory() {
        //Nothing to do
    }

    public static Matcher getMatcher(AlgoType algoType, Set<MatchOption> options, Set<String> keywords) {
        switch (algoType) {
            case AHO_CORASICK:
                Automaton automaton = new Automaton(options, keywords);
                return new AhoCorasickMatcher(automaton);
            case REGEX:
                return new RegexMatcher(keywords);
            case EXPERIMENTAL:

            default:
                throw new IllegalArgumentException("Invalid type: " + algoType);
        }
    }
}
