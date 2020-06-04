package com.airgap.airgapagent.configuration;

import org.springframework.boot.ApplicationArguments;

/**
 * com.airgap.airgapagent.configuration
 * Created by Jacques Fontignie on 6/4/2020.
 */
public abstract class AbstractSearchActionCommandLineBuilder implements CommandLineBuilder<Action> {

    String getFolderLocation(ApplicationArguments arguments) throws CommandLineParseException {
        return getRequiredArgument(arguments, "folder");
    }

    String getCorpusLocation(ApplicationArguments arguments) throws CommandLineParseException {
        return getRequiredArgument(arguments, "corpus");
    }

    protected int getMinHit(ApplicationArguments arguments) throws CommandLineParseException {
        String found = getRequiredArgument(arguments, "minHit");
        try {
            return Integer.parseInt(found);
        } catch (NumberFormatException e) {
            throw new CommandLineParseException("The value for minHit must be an integer");
        }
    }


}
