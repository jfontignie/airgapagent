package com.airgap.airgapagent.configuration;

import org.springframework.boot.ApplicationArguments;

/**
 * com.airgap.airgapagent.configuration
 * Created by Jacques Fontignie on 6/4/2020.
 */
public class FileSearchActionCommandLineBuilder extends AbstractSearchActionCommandLineBuilder {
    @Override
    public Action map(ApplicationArguments arguments) throws CommandLineParseException {
        return new FileSearchAction(getMinHit(arguments), getCorpusLocation(arguments), getFolderLocation(arguments), getFoundLocation(arguments));
    }

    private String getFoundLocation(ApplicationArguments arguments) throws CommandLineParseException {
        return getRequiredArgument(arguments, "found");
    }

}
