package com.ujiuye.sms.listener;

import com.ujiuye.sms.util.SmsUtil;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class SmsListener {
    @Autowired
    SmsUtil smsUtil;

    @RabbitListener(queues = "dongyimai.sms.queue")
    public void sendSms(Map<String,String> map){
        if (map == null ) return ;
        String mobile = map.get("mobile");
        String code = map.get("code");
        smsUtil.sendSms(mobile,code);
    }
}
