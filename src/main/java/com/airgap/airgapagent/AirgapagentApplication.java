package com.airgap.airgapagent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AirgapagentApplication {

    @SuppressWarnings("java:S4823")
    public static void main(String[] args) {
        System.exit(SpringApplication.exit(
                SpringApplication.run(AirgapagentApplication.class, args)
        ));
    }


}
