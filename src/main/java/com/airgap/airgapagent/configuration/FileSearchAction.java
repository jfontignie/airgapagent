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
public class FileSearchAction extends AbstractSearchAction<File> {

    public static final String COMMAND_NAME = "search";

    @Parameter(names = "-help", help = true)
    private boolean help;

    @Parameter(
            names = "-folder",
            description = "folder to scan for the specific patterns",
            required = true,
            validateWith = FolderExistsValidator.class)
    private File folderLocation;

    public File getRootLocation() {
        return folderLocation;
    }

    public void setRootLocation(File folderLocation) {
        this.folderLocation = folderLocation;
    }
}
