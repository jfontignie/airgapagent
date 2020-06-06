package com.airgap.airgapagent.configuration;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;

import java.io.File;

/**
 * com.airgap.airgapagent.configuration
 * Created by Jacques Fontignie on 6/4/2020.
 */
public class FileExistsValidator implements IParameterValidator {

    @Override
    public void validate(String name, String value) {
        File file = new File(value);
        if (!file.exists())
            throw new ParameterException(name + ": the file " + value + " does not exist");
        if (!file.isFile())
            throw new ParameterException(name + ": the file " + value + " is not a regular file");
    }
}
