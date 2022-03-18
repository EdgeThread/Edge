package com.ujiuye;

import com.ujiuye.dongyimaicommon.entity.FeignInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.ujiuye.dao")
public class DongyimaiSellergoodsServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DongyimaiSellergoodsServiceApplication.class, args);
    }

    @Bean
    public FeignInterceptor feignInterceptor(){
        return new FeignInterceptor();
    }

}
