package com.airgap.airgapagent.configuration;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

import java.io.File;
import java.util.List;

/**
 * com.airgap.airgapagent.configuration
 * Created by Jacques Fontignie on 6/3/2020.
 */
@Parameters(commandNames = FileCopyAction.COMMAND_NAME,
        commandDescription = "Copy all the files matching a specific corpus in a target folder")
public class FileCopyAction extends AbstractSearchAction {

    public static final String COMMAND_NAME = "copy";

    @Parameter(
            names = "-target",
            description = "Target folder in which to save the files",
            required = true,
            validateWith = FolderExistsValidator.class
    )
    private File target;

    @Parameter(
            names = "-options",
            description = "Options during copy",
            listConverter = CopyOptionConverter.class
    )
    private List<CopyOption> copyOptions;

    public File getTarget() {
        return target;
    }

    List<CopyOption> getCopyOptions() {
        return copyOptions;
    }


}
