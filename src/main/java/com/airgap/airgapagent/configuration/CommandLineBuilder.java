package com.airgap.airgapagent.configuration;

import org.springframework.boot.ApplicationArguments;

import java.util.List;

/**
 * com.airgap.airgapagent.configuration
 * Created by Jacques Fontignie on 6/4/2020.
 */
public interface CommandLineBuilder<T> {

    default String getRequiredArgument(ApplicationArguments arguments, String value) throws CommandLineParseException {
        List<String> found = arguments.getOptionValues(value);
        if (found == null) throw new CommandLineParseException(value + "' is not present");
        if (found.isEmpty()) throw new CommandLineParseException(value + "' must have a value");
        if (found.size() > 1) throw new CommandLineParseException(value + "' can have only one value");
        return found.get(0);
    }

    T map(ApplicationArguments arguments) throws CommandLineParseException;
}
