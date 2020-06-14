package com.airgap.airgapagent.service;

import com.airgap.airgapagent.algo.MatchingResult;
import com.airgap.airgapagent.algo.ahocorasick.AhoCorasickMatcher;
import com.airgap.airgapagent.algo.ahocorasick.Automaton;
import com.airgap.airgapagent.algo.ahocorasick.AutomatonOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.io.Reader;
import java.util.Set;

/**
 * com.airgap.airgapagent.service
 * Created by Jacques Fontignie on 5/29/2020.
 */
@Service
public class AhoCorasickMatcherService {

    private static final Logger log = LoggerFactory.getLogger(AhoCorasickMatcherService.class);


    public Automaton buildAutomaton(Set<String> patterns, Set<AutomatonOption> options) {
        return new Automaton(options, patterns);
    }

    public Flux<MatchingResult> listMatches(Reader reader, Automaton automaton) {
        Flux<MatchingResult> flux = Flux.create(fluxSink -> {
            try {
                AhoCorasickMatcher matcher = new AhoCorasickMatcher(automaton);
                matcher.match(reader, fluxSink::next);
                fluxSink.complete();
            } catch (IOException e) {
                fluxSink.error(e);
            }
        });
        return flux.doFinally(signalType -> {
            try {
                reader.close();
            } catch (IOException e) {
                log.error("Error while close reader");
            }
        });
    }
}
