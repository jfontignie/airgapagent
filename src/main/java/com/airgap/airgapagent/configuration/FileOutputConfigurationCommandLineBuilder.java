package com.airgap.airgapagent.configuration;

import org.springframework.boot.ApplicationArguments;

/**
 * com.airgap.airgapagent.configuration
 * Created by Jacques Fontignie on 6/4/2020.
 */
public class FileOutputConfigurationCommandLineBuilder implements CommandLineBuilder<OutputConfiguration> {
    @Override
    public OutputConfiguration map(ApplicationArguments arguments) throws CommandLineParseException {
        return new OutputConfiguration(getRequiredArgument(arguments, "errorFile"), getRequiredArgument(arguments, "stateFile"));
    }
}
