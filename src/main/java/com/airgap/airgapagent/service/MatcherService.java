package com.airgap.airgapagent.service;

import com.airgap.airgapagent.algo.Matcher;
import com.airgap.airgapagent.algo.MatchingResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.io.Reader;

/**
 * com.airgap.airgapagent.service
 * Created by Jacques Fontignie on 5/29/2020.
 */
@Service
public class MatcherService {

    private static final Logger log = LoggerFactory.getLogger(MatcherService.class);

    public Flux<MatchingResult> listMatches(Reader reader, Matcher matcher) {
        Flux<MatchingResult> flux = Flux.create(fluxSink -> {
            try {
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
