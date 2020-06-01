package com.airgap.airgapagent.batch;

import com.airgap.airgapagent.domain.ExactMatchContext;
import com.airgap.airgapagent.domain.ExactMatchContextBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.io.File;
import java.time.Duration;

/**
 * com.airgap.airgapagent.batch
 * Created by Jacques Fontignie on 4/27/2020.
 */
@Configuration
public class ExactMatchBatchConfiguration {


    private static final String MATCH_SCAN_FOLDER_FOLDER = "match.scanFolder";
    private static final String MATCH_CORPUS_FILE = "match.file.corpus";
    private static final String MATCH_FOUND_FILE = "match.file.found";
    private static final String MATCH_STATE_FILE = "match.file.state";
    public static final String MATCH_ERROR_FILE = "match.file.error";
    private static final String MATCH_EXACT_MIN_HIT = "match.minHit";
    private static final String MATCH_EXACT_MAX_HIT = "match.maxHit";
    private static final String MATCH_SAVE_INTERVAL = "match.saveInterval";

    @Bean
    public ExactMatchContext config(
            Environment environment) {
        return new ExactMatchContextBuilder()
                .setScanFolder(new File(environment.getRequiredProperty(MATCH_SCAN_FOLDER_FOLDER)))
                .setExactMatchFile(new File(environment.getRequiredProperty(MATCH_CORPUS_FILE)))
                .setFoundFile(new File(environment.getRequiredProperty(MATCH_FOUND_FILE)))
                .setStateFile(new File(environment.getRequiredProperty(MATCH_STATE_FILE)))
                .setMinHit(environment.getRequiredProperty(MATCH_EXACT_MIN_HIT, Integer.class))
                .setMaxHit(environment.getRequiredProperty(MATCH_EXACT_MAX_HIT, Integer.class))
                .setSaveInterval(Duration.ofSeconds(environment.getRequiredProperty(MATCH_SAVE_INTERVAL, Integer.class)))
                .createExactMatchContext();
    }
}
