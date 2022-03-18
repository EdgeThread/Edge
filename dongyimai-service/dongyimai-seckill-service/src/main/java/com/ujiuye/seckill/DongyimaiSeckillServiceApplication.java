package com.ujiuye.seckill;

import com.ujiuye.dongyimaicommon.entity.FeignInterceptor;
import com.ujiuye.dongyimaicommon.utils.IdWorker;
import com.ujiuye.seckill.util.TokenDecode;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(basePackages = "com.ujiuye.seckill.feign")
@EnableScheduling
@EnableAsync
@MapperScan("com.ujiuye.seckill.dao")
public class DongyimaiSeckillServiceApplication {
    @Bean
    public FeignInterceptor feignInterceptor(){
        return new FeignInterceptor();
    }

    @Bean
    public IdWorker idWorker(){
        return new IdWorker(1,1);
    }

    @Bean
    public TokenDecode tokenDecode(){
        return new TokenDecode();
    }


    public static void main(String[] args) {
        SpringApplication.run(DongyimaiSeckillServiceApplication.class, args);
    }

}
