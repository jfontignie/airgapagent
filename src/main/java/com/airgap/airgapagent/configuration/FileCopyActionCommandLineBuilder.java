package com.airgap.airgapagent.configuration;

import org.springframework.boot.ApplicationArguments;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * com.airgap.airgapagent.configuration
 * Created by Jacques Fontignie on 6/4/2020.
 */
public class FileCopyActionCommandLineBuilder extends AbstractSearchActionCommandLineBuilder {


    @Override
    public Action map(ApplicationArguments arguments) throws CommandLineParseException {
        return new FileCopyAction(getMinHit(arguments),
                getCorpusLocation(arguments),
                getFolderLocation(arguments),
                getTargetLocation(arguments),
                getCopyOptions(arguments)
        );
    }

    private Set<CopyOption> getCopyOptions(ApplicationArguments arguments) throws CommandLineParseException {
        List<String> found = arguments.getOptionValues("options");
        if (found == null) {
            return EnumSet.noneOf(CopyOption.class);
        }
        try {
            return found.stream().map(CopyOption::valueOf).collect(Collectors.toSet());
        } catch (IllegalArgumentException e) {
            throw new CommandLineParseException("Impossible to read 'options'");
        }
    }

    private String getTargetLocation(ApplicationArguments arguments) throws CommandLineParseException {
        return getRequiredArgument(arguments, "target");
    }
}
