package com.airgap.airgapagent.configuration;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;

import java.io.File;

/**
 * com.airgap.airgapagent.configuration
 * Created by Jacques Fontignie on 6/4/2020.
 */
public class FolderExistsValidator implements IParameterValidator {

    @Override
    public void validate(String name, String value) {
        File file = new File(value);
        if (!file.exists())
            throw new ParameterException(name + ": the folder " + value + " does not exist");
        if (!file.isDirectory())
            throw new ParameterException(name + ": the folder " + value + " is not a folder");
    }
}
