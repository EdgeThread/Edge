package com.ujiuye.pay.config;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AlipayConfig {

    @Value("${alipay.serverUrl}")
    private String serverUrl;

    @Value("${alipay.appId}")
    private String appId;

    @Value("${alipay.privateKey}")
    private String privateKey;

    @Value("${alipay.format}")
    private String format;

    @Value("${alipay.charset}")
    private String charset;

    @Value("${alipay.alipayPublicKey}")
    private String alipayPublicKey;

    @Value("${alipay.signType}")
    private String signType;

    @Bean
    public AlipayClient getPayClient() {
        return new DefaultAlipayClient(serverUrl, appId, privateKey, format, charset, alipayPublicKey, signType);
    }
}
