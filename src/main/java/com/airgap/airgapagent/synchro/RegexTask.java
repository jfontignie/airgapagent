package com.airgap.airgapagent.synchro;

import org.apache.tika.Tika;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.Objects;
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
    private static final Logger logger = LoggerFactory.getLogger(RegexTask.class);
    private boolean caseSensitive = false;

    public RegexTask() {
        super(TaskType.REGEX);
    }

    public List<String> getRegex() {
        return regex;
    }

    public void setRegex(List<String> regex) {
        this.regex = regex;
    }

    public boolean isCaseSensitive() {
        return caseSensitive;
    }

    public void setCaseSensitive(boolean caseSensitive) {
        this.caseSensitive = caseSensitive;
    }

    @Override
    public void init() throws IOException {
        super.init();
        tika = new Tika();
        int flag = 0;
        if (!isCaseSensitive()) {
            flag |= Pattern.CASE_INSENSITIVE;
        }
        pattern = Pattern.compile(String.join("|", "(" + this.regex + ")"), flag);

    }

    @Override
    public void call(PathInfo path) throws IOException {
        Objects.requireNonNull(tika, "Init method has not been called");
        logger.debug("Analyzing {}", path.getOriginalPath());
        try (Reader reader = tika.parse(path.getOriginalPath())) {
            Scanner scanner = new Scanner(reader);

            String found = scanner.findWithinHorizon(pattern, 0);
            if (found != null) {
                logger.info("Found matching path: {}", path.getOriginalPath());
                callNext(path);
            }
        }
    }

}
