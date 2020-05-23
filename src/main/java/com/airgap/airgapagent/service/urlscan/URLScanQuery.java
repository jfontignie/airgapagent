package com.airgap.airgapagent.service.urlscan;

import java.util.List;

/**
 * com.airgap.airgapagent.service.apis
 * Created by Jacques Fontignie on 5/19/2020.
 */
public class URLScanQuery {
    private final String url;

    private final PublicType publicTag;

    private final String referer;

    private final List<String> tags;


    public URLScanQuery(String url, PublicType publicTag, String referer, List<String> tags) {
        this.url = url;
        this.publicTag = publicTag;
        this.referer = referer;
        this.tags = tags;
    }

    public String getUrl() {
        return url;
    }

    public PublicType getPublicTag() {
        return publicTag;
    }

    public String getReferer() {
        return referer;
    }

    public List<String> getTags() {
        return tags;
    }
}
