package com.airgap.airgapagent.synchro.predicate;

import com.airgap.airgapagent.synchro.utils.PathInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.tika.Tika;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * com.airgap.airgapagent.synchro
 * Created by Jacques Fontignie on 4/17/2020.
 */
public class RegexPredicate implements Predicate {
    private List<String> regex;
    private Tika tika;
    private Pattern pattern;
    private static final Logger logger = LoggerFactory.getLogger(RegexPredicate.class);
    private boolean caseSensitive = false;

    public RegexPredicate() {
        //Nothing to do
    }

    public RegexPredicate(List<String> regex, boolean caseSensitive) {
        this.regex = regex;
        this.caseSensitive = caseSensitive;
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
    public void init() {
        tika = new Tika();
        int flag = 0;
        if (!isCaseSensitive()) {
            flag |= Pattern.CASE_INSENSITIVE;
        }
        pattern = Pattern.compile(
                this.regex
                        .stream()
                        .map(String::trim)
                        .map(s -> "(" + s + ")")
                        .collect(Collectors.joining("|")),
                flag);

    }

    @Override
    public boolean call(PathInfo path) throws IOException {
        Objects.requireNonNull(tika, "Init method has not been called");
        logger.debug("Analyzing {}", path.getOriginalPath());
        try (Reader reader = tika.parse(path.getOriginalPath())) {
            Scanner scanner = new Scanner(reader);

            String found = scanner.findWithinHorizon(pattern, 0);
            if (found != null) {
                logger.info("Found matching path: {}", path.getOriginalPath());
                return true;
            }
        }
        return false;
    }

    @Override
    public void close() {
        //Nothing to do
    }

    @JsonIgnore
    public Pattern getPattern() {
        return pattern;
    }
}
