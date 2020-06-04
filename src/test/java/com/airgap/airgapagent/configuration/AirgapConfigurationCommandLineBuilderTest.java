package com.airgap.airgapagent.configuration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.DefaultApplicationArguments;

/**
 * com.airgap.airgapagent.configuration
 * Created by Jacques Fontignie on 6/3/2020.
 */
class AirgapConfigurationCommandLineBuilderTest {

    private final AirgapConfigurationCommandLineBuilder airgapConfigurationCommandLineBuilder = new AirgapConfigurationCommandLineBuilder();

    @Test
    void map() throws CommandLineParseException {
        AirgapConfiguration airgapConfiguration = airgapConfigurationCommandLineBuilder.map(new DefaultApplicationArguments("--action=SEARCH"));
        Assertions.assertNotNull(airgapConfiguration.getAction());
        Assertions.assertNotNull(airgapConfiguration.getOutput());
    }
}
