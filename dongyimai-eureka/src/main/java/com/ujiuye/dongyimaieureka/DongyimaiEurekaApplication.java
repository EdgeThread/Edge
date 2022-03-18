package com.ujiuye.dongyimaieureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class DongyimaiEurekaApplication {

    public static void main(String[] args) {
        SpringApplication.run(DongyimaiEurekaApplication.class, args);
    }

}
