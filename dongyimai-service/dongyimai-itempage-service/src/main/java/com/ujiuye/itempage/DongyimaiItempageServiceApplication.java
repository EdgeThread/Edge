package com.ujiuye.itempage;

import com.ujiuye.dongyimaicommon.entity.FeignInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableEurekaClient
@EnableFeignClients(basePackages = "com.ujiuye.feign") // 商品服务的feign接口所在位置
public class DongyimaiItempageServiceApplication {

    @Bean
    public FeignInterceptor feignInterceptor(){
        return new FeignInterceptor();
    }
    public static void main(String[] args) {
        SpringApplication.run(DongyimaiItempageServiceApplication.class, args);
    }

}
