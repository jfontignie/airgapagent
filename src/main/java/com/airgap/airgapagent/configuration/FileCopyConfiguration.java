package com.airgap.airgapagent.configuration;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * com.airgap.airgapagent.configuration
 * Created by Jacques Fontignie on 6/3/2020.
 */
@Parameters(commandNames = FileCopyConfiguration.COMMAND_NAME,
        commandDescription = "Copy all the files matching a specific corpus in a target folder")
public class FileCopyConfiguration extends AbstractScanConfiguration<File> {

    public static final String COMMAND_NAME = "copy";

    @Parameter(
            names = "-folder",
            description = "folder to scan for the specific patterns",
            required = true,
            validateWith = FolderExistsValidator.class)
    private File rootLocation;


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
    private List<CopyOption> copyOptions = new ArrayList<>();

    public Set<CopyOption> getCopyOptions() {

        return Set.copyOf(copyOptions);
    }

    public File getTarget() {
        return target;
    }

    public void setCopyOptions(List<CopyOption> copyOptions) {
        this.copyOptions = copyOptions;
    }

    public void setRoot(File folderLocation) {
        this.rootLocation = folderLocation;
    }

    public void setTarget(File target) {
        this.target = target;
    }

    public File getRootLocation() {
        return rootLocation;
    }
}
