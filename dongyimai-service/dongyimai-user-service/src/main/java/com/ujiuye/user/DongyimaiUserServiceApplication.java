package com.ujiuye.user;

import com.ujiuye.dongyimaicommon.entity.FeignInterceptor;
import com.ujiuye.user.util.TokenDecode;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@MapperScan("com.ujiuye.user.dao")
public class DongyimaiUserServiceApplication {
    @Bean
    public TokenDecode tokenDecode(){
        return new TokenDecode();
    }

    @Bean
    public FeignInterceptor feignInterceptor(){
        return new FeignInterceptor();
    }

    public static void main(String[] args) {
        SpringApplication.run(DongyimaiUserServiceApplication.class, args);
    }

}
