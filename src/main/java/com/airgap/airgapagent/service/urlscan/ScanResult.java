package com.airgap.airgapagent.service.urlscan;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.StringJoiner;

/**
 * com.airgap.airgapagent.service.apis
 * Created by Jacques Fontignie on 5/19/2020.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ScanResult {

    private Verdicts verdicts;

    public Verdicts getVerdicts() {
        return verdicts;
    }

    public void setVerdicts(Verdicts verdicts) {
        this.verdicts = verdicts;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ScanResult.class.getSimpleName() + "[", "]")
                .add("verdicts=" + verdicts)
                .toString();
    }
}
