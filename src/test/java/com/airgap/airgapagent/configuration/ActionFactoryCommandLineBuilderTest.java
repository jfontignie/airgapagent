package com.airgap.airgapagent.configuration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.DefaultApplicationArguments;

/**
 * com.airgap.airgapagent.configuration
 * Created by Jacques Fontignie on 6/4/2020.
 */
class ActionFactoryCommandLineBuilderTest {

    private final ActionFactoryCommandLineBuilder actionFactoryCommandLineBuilder = new ActionFactoryCommandLineBuilder();

    @Test
    void invalidAction() throws CommandLineParseException {

        Assertions.assertThrows(CommandLineParseException.class, () ->
                actionFactoryCommandLineBuilder.map(new DefaultApplicationArguments("")));

        Assertions.assertThrows(CommandLineParseException.class, () ->
                actionFactoryCommandLineBuilder.map(new DefaultApplicationArguments("--action")));

        Assertions.assertThrows(CommandLineParseException.class, () ->
                actionFactoryCommandLineBuilder.map(new DefaultApplicationArguments("--action=pouet")));

        Assertions.assertNull(actionFactoryCommandLineBuilder.map(new DefaultApplicationArguments("--action=NULL")));
    }
}
