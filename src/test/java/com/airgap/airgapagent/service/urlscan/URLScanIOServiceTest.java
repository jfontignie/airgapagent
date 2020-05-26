package com.airgap.airgapagent.service.urlscan;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.mock.env.MockEnvironment;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertThrows;


/**
 * com.airgap.airgapagent.service.apis
 * Created by Jacques Fontignie on 5/21/2020.
 */
class URLScanIOServiceTest {


    private StandardEnvironment environment;

    @BeforeEach
    public void setUp() {
        this.environment = new StandardEnvironment();
    }

    @Test
    public void scanInvalidKey() throws MalformedURLException {
        URLScanIOService urlScanIOService = new URLScanIOService(new MockEnvironment(), WebClient.builder());
        Mono<Submission> result = urlScanIOService.scan(new URL("http://www.github.com"));
        assertThrows(WebClientResponseException.Unauthorized.class, result::block);
    }

    @SuppressWarnings("java:S1607")
    @Disabled
    @Test
    public void completeScan() throws MalformedURLException {
        URLScanIOService urlScanIOService = new URLScanIOService(environment, WebClient.builder());
        Mono<Submission> mono = urlScanIOService.scan(new URL("http://www.github.com"));
        Submission scanId = mono.block();
        Assertions.assertNotNull(scanId);

        Assertions.assertNotNull(scanId.getUuid());

        Mono<ScanResult> result = urlScanIOService.getResult(scanId);

        ScanResult scanResult = result.block();

        Assertions.assertNotNull(scanResult);
        System.out.println(scanResult);


    }

}
