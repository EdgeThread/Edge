package com.ujiuye.content;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@MapperScan("com.ujiuye.content.dao")
public class DongyimaiContentServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DongyimaiContentServiceApplication.class, args);
    }

}
