package com.airgap.airgapagent;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AirgapagentApplication {

    @SuppressWarnings("java:S4823")
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(AirgapagentApplication.class);
        application.setBannerMode(Banner.Mode.OFF);


        System.exit(SpringApplication.exit(
                application.run(args)
        ));
    }


}
