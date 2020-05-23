package com.airgap.airgapagent.service.domaintools;

import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URL;

/**
 * com.airgap.airgapagent.service.domaintools
 * Created by Jacques Fontignie on 5/22/2020.
 */
public class DomainToolsService {
    private final WebClient webClient;


    public DomainToolsService(Environment environment, WebClient.Builder webClientBuilder) {


        webClient = webClientBuilder
                .baseUrl("https://api.domaintools.com/v1/")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString())
                .build();

    }


    public Mono<WhoIsInfo> scan(URL url) {
        String authority = url.getAuthority();
        return webClient
                .get()
                .uri(authority)
                .retrieve()
                .bodyToMono(WhoIsInfo.class);
    }

}

