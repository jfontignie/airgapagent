package com.airgap.airgapagent.synchro;

import org.apache.tika.Tika;

import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * com.airgap.airgapagent.synchro
 * Created by Jacques Fontignie on 4/17/2020.
 */
public class RegexTask extends AbstractTask {
    private List<String> regex;
    private Tika tika;
    private Pattern pattern;

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
        tika = new Tika();
        pattern = Pattern.compile(String.join("|", regex));
    }

    @Override
    public void call(PathInfo path) throws IOException {
        try (Reader reader = tika.parse(path.getOriginalPath())) {
            Scanner scanner = new Scanner(reader);
            String found = scanner.findWithinHorizon(pattern, 0);
            if (found != null) {
                callNext(path);
            }
        }
    }

}
