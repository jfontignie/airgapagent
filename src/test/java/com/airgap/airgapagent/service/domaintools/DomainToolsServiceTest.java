package com.airgap.airgapagent.service.domaintools;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * com.airgap.airgapagent.service.domaintools
 * Created by Jacques Fontignie on 5/22/2020.
 */
class DomainToolsServiceTest {

    @Test
    public void scanDomainTools() throws MalformedURLException {
        DomainToolsService domainToolsService = new DomainToolsService(WebClient.builder());
        Mono<WhoIsInfo> result = domainToolsService.scan(new URL("https://domaintools.com/test"));
        WhoIsInfo info = result.block();
        Assertions.assertThat(info).isNotNull();
        Assertions.assertThat(info.getIpAddress()).isNotNull();
    }
}
