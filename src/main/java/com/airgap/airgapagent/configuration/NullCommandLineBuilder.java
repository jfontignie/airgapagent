package com.airgap.airgapagent.configuration;

import org.springframework.boot.ApplicationArguments;

/**
 * com.airgap.airgapagent.configuration
 * Created by Jacques Fontignie on 6/4/2020.
 */
public class NullCommandLineBuilder implements CommandLineBuilder<Action> {
    @Override
    public Action map(ApplicationArguments arguments) throws CommandLineParseException {
        return null;
    }
}
