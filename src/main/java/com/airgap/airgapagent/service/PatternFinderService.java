package com.airgap.airgapagent.service;

import reactor.core.publisher.Flux;

import java.io.Reader;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * com.airgap.airgapagent.service
 * Created by Jacques Fontignie on 5/29/2020.
 */
public class PatternFinderService {

    public Flux<String> listPattern(Reader reader, Pattern pattern) {
        Scanner scanner = new Scanner(reader);
        return Flux.<String>create(fluxSink -> {
            String found;
            do {
                found = scanner.findWithinHorizon(pattern, 0);
                if (found != null) {
                    fluxSink.next(found);
                }
            } while (found != null);
            fluxSink.complete();

        }).doFinally(s -> scanner.close());
    }
}
