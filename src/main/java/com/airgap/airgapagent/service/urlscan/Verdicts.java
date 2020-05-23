package com.airgap.airgapagent.service.urlscan;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.StringJoiner;

/**
 * com.airgap.airgapagent.service.apis
 * Created by Jacques Fontignie on 5/21/2020.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Verdicts {

    private int score;
    private boolean malicious;

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean isMalicious() {
        return malicious;
    }

    public void setMalicious(boolean malicious) {
        this.malicious = malicious;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Verdicts.class.getSimpleName() + "[", "]")
                .add("score=" + score)
                .add("malicious=" + malicious)
                .toString();
    }
}
