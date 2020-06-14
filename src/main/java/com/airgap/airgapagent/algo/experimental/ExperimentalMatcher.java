package com.airgap.airgapagent.algo.experimental;

import com.airgap.airgapagent.algo.MatchOption;
import com.airgap.airgapagent.algo.Matcher;
import com.airgap.airgapagent.algo.MatchingResult;

import java.io.IOException;
import java.io.Reader;
import java.util.Set;
import java.util.function.Consumer;

/**
 * com.airgap.airgapagent.algo.experimental
 * Created by Jacques Fontignie on 6/14/2020.
 */
public class ExperimentalMatcher implements Matcher {

    public ExperimentalMatcher(Set<MatchOption> options, Set<String> keywords) {
        //To be implemented
    }

    @Override
    public void match(Reader reader, Consumer<MatchingResult> target) throws IOException {
        //To be implemented
    }
}
