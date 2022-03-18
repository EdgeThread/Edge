package com.ujiuye.canal;

import com.xpand.starter.canal.annotation.EnableCanalClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
@EnableEurekaClient
@EnableCanalClient
@EnableFeignClients(basePackages = {"com.ujiuye.content.feign","com.ujiuye.itempage.feign"})
public class DongyimaiCanalServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DongyimaiCanalServiceApplication.class, args);
    }

}
