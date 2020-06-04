package com.airgap.airgapagent.configuration;

import org.springframework.boot.ApplicationArguments;

/**
 * com.airgap.airgapagent.configuration
 * Created by Jacques Fontignie on 6/3/2020.
 */
public class AirgapConfigurationCommandLineBuilder implements CommandLineBuilder<AirgapConfiguration> {


    public AirgapConfiguration map(ApplicationArguments arguments) throws CommandLineParseException {

        Action action = new ActionFactoryCommandLineBuilder().map(arguments);
        OutputConfiguration logger = new OutputConfigurationFactoryBuilder().map(arguments);
        return new AirgapConfiguration(action, logger);
    }

}
