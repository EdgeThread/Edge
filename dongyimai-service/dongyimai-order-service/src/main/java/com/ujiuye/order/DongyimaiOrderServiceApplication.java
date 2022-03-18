package com.ujiuye.order;

import com.ujiuye.dongyimaicommon.entity.FeignInterceptor;
import com.ujiuye.dongyimaicommon.utils.IdWorker;
import com.ujiuye.order.util.TokenDecode;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
@MapperScan("com.ujiuye.order.dao")
@EnableFeignClients(basePackages = {"com.ujiuye.feign","com.ujiuye.user.feign"})
public class DongyimaiOrderServiceApplication {


    public static void main(String[] args) {
        SpringApplication.run(DongyimaiOrderServiceApplication.class, args);
    }

    @Bean
    public TokenDecode tokenService(){
        return new TokenDecode();
    }

    @Bean
    public IdWorker getIdWorker(){
        return new IdWorker(1,1);
    }

    @Bean
    public FeignInterceptor feignInterceptor(){
        return new FeignInterceptor();
    }

}
