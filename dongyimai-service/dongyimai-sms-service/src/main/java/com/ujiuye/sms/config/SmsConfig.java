package com.ujiuye.sms.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SmsConfig {
    private String simpleQueue = "dongyimai.sms.queue";


    @Bean
    public Queue getSimpleQueue (){
        return new Queue(simpleQueue);
    }
}
