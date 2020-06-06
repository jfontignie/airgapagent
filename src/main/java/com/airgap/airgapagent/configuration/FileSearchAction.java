package com.airgap.airgapagent.configuration;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

import java.io.File;

/**
 * com.airgap.airgapagent.configuration
 * Created by Jacques Fontignie on 6/4/2020.
 */
@Parameters(commandNames = FileSearchAction.COMMAND_NAME,
        commandDescription = "Search for exact matches in a folder. The files will be parsed using Tika parser " +
                "and will look for patterns defined in corpus using Aho Corasick algorithm")
public class FileSearchAction extends AbstractSearchAction {


    public static final String COMMAND_NAME = "search";
    @Parameter(names = "-help", help = true)
    private boolean help;

    @Parameter(
            names = "-found",
            description = "File containing the output in a CSV format.",
            required = true)
    private File foundLocation;


    public File getFoundLocation() {
        return foundLocation;
    }
}
