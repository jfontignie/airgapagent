package com.airgap.airgapagent.service.urlscan;


import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.net.URL;
import java.time.Duration;
import java.util.Collections;
import java.util.Map;

/**
 * com.airgap.airgapagent.service.apis
 * Created by Jacques Fontignie on 5/19/2020.
 */
@Service
public class URLScanIOService {

    static final String URLSCANIO_APIKEY = "urlscanio.apikey";
    private final WebClient webClient;

    private final WebClient fatWebClient;

    public URLScanIOService(Environment environment, WebClient.Builder webClientBuilder) {
        String apiKey = environment.getProperty(URLSCANIO_APIKEY, "");


        webClient = webClientBuilder
                .baseUrl("https://urlscan.io/api/v1/")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString())
                .defaultHeader("API-Key", apiKey)
                .build();

        ExchangeStrategies exchangeStrategies = ExchangeStrategies.builder()
                .codecs(configurer -> configurer
                        .defaultCodecs()
                        .maxInMemorySize(1024 * 1024)).build();

        fatWebClient = webClient
                .mutate()
                .exchangeStrategies(exchangeStrategies).build();
    }


    public Mono<Submission> scan(URL url) {
        URLScanQuery scanQuery = new URLScanQuery(url.toString(),
                PublicType.OFF, "",
                Collections.emptyList());

        return webClient
                .post()
                .uri("scan/")
                .body(Mono.just(scanQuery), URLScanQuery.class)
                .retrieve()
                .bodyToMono(Submission.class);
    }

    public Mono<ScanResult> getResult(Submission scanId) {

        return fatWebClient
                .get()
                .uri("result/{scanid}", Map.of("scanid", scanId.getUuid()))
                .retrieve()
                .bodyToMono(ScanResult.class)
                .retryWhen(Retry.fixedDelay(60, Duration.ofSeconds(2))
                        .filter(throwable -> throwable instanceof WebClientResponseException.NotFound));
    }
}
