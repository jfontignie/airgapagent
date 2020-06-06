package com.airgap.airgapagent.configuration;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.validators.PositiveInteger;

import java.io.File;

/**
 * com.airgap.airgapagent.configuration
 * Created by Jacques Fontignie on 6/3/2020.
 */
public class AbstractSearchAction implements Action {

    @SuppressWarnings("FieldMayBeFinal")
    @Parameter(
            names = "-minHit",
            description = "number of minimum hits to match",
            validateWith = PositiveInteger.class)
    private int minHit = 5;


    @Parameter(
            names = "-corpus",
            description = "corpus file: This file must be in CSV format and needs to contain an header. " +
                    "The algorithm used is Aho Corasick to retrieve the file",
            required = true,
            validateWith = FileExistsValidator.class)
    private File corpusLocation;

    @Parameter(
            names = "-folder",
            description = "folder to scan for the specific patterns",
            required = true,
            validateWith = FolderExistsValidator.class)
    private File folderLocation;

    @Parameter(
            names = "-state",
            description = "file containing the state to resume in case of error",
            validateWith = FileExistsValidator.class)
    private File stateLocation;


    public int getMinHit() {
        return minHit;
    }

    public File getCorpusLocation() {
        return corpusLocation;
    }

    public File getRootLocation() {
        return folderLocation;
    }

    public File getStateLocation() {
        return stateLocation;
    }
}
