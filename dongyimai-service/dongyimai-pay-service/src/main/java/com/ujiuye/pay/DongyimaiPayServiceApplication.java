package com.ujiuye.pay;

import com.ujiuye.dongyimaicommon.entity.FeignInterceptor;
import com.ujiuye.pay.util.TokenDecode;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableEurekaClient
@EnableFeignClients(basePackages = {"com.ujiuye.order.feign","com.ujiuye.seckill.feign"})
public class DongyimaiPayServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DongyimaiPayServiceApplication.class, args);
    }

    @Bean
    public TokenDecode getTokenDecode(){
        return new TokenDecode();
    }

    @Bean
    public FeignInterceptor feignInterceptor(){
        return new FeignInterceptor();
    }

}
