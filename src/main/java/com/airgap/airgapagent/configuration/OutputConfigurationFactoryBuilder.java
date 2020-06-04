package com.airgap.airgapagent.configuration;

import org.springframework.boot.ApplicationArguments;

import java.util.EnumMap;
import java.util.Map;

/**
 * com.airgap.airgapagent.configuration
 * Created by Jacques Fontignie on 6/4/2020.
 */
public class OutputConfigurationFactoryBuilder implements CommandLineBuilder<OutputConfiguration> {

    private static final Map<OutputType, CommandLineBuilder<OutputConfiguration>> map = new EnumMap<>(Map.of(
            OutputType.FILE, new FileOutputConfigurationCommandLineBuilder()
    )
    );
    private static final String OUTPUT_PARAMETER = "output";


    @Override
    public OutputConfiguration map(ApplicationArguments arguments) throws CommandLineParseException {

        String outputType = getRequiredArgument(arguments, OUTPUT_PARAMETER);
        try {
            OutputType output = OutputType.valueOf(outputType);
            return map.get(output).map(arguments);
        } catch (IllegalArgumentException e) {
            throw new CommandLineParseException("Invalid action: " + outputType);
        }
    }
}
