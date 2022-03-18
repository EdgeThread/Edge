package com.ujiuye.sms.controller;

import com.ujiuye.dongyimaicommon.entity.Result;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController

public class SmsController {
    @Autowired
    RabbitTemplate rabbitTemplate;

    @RequestMapping
    public void sendSms(){
        Map<String,String> map = new HashMap<>();
        map.put("mobile","18224481548");
        map.put("code","123456789");
        rabbitTemplate.convertAndSend("dongyimai.sms.queue",map);
    }
}
