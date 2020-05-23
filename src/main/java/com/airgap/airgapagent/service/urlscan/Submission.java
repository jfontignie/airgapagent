package com.airgap.airgapagent.service.urlscan;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * com.airgap.airgapagent.service.apis
 * Created by Jacques Fontignie on 5/21/2020.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Submission {
    private String message;
    private String uuid;
    private String visibility;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }
}
