package com.springingdream.passport;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class MarketplacePassportApplication {

    public static void main(String[] args) {
        SpringApplication.run(MarketplacePassportApplication.class, args);
    }

}

