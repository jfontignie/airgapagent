package com.airgap.airgapagent.service.domaintools;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;
import java.util.StringJoiner;

/**
 * com.airgap.airgapagent.service.domaintools
 * Created by Jacques Fontignie on 5/22/2020.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class WhoIsInfo {

    private String ipAddress;

    public String getIpAddress() {
        return ipAddress;
    }

    @JsonProperty("response")
    private void unpackNameFromNestedObject(Map<String, Object> response) {
        //noinspection unchecked
        ipAddress = String.valueOf(((Map<String, Object>) response.get("server")).get("ip_address"));
    }


    @Override
    public String toString() {
        return new StringJoiner(", ", WhoIsInfo.class.getSimpleName() + "[", "]")
                .add("ipAddress='" + ipAddress + "'")
                .toString();
    }
}
