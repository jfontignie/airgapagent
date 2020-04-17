package com.airgap.airgapagent.synchro;

import java.nio.file.Path;
import java.util.List;

/**
 * com.airgap.airgapagent.synchro
 * Created by Jacques Fontignie on 4/17/2020.
 */
public class RegexTask extends AbstractTask {
    private List<String> regex;

    public RegexTask() {
        super(TaskType.REGEX);
    }

    public List<String> getRegex() {
        return regex;
    }

    public void setRegex(List<String> regex) {
        this.regex = regex;
    }

    @Override
    public void init() {
        //Nothing to do
    }

    @Override
    public void call(Path baseFolder, Path path) {
        //TODO implement
    }
}
