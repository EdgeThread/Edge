package com.ujiuye.userauth.service.impl;

import com.ujiuye.userauth.service.AuthService;
import com.ujiuye.userauth.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    LoadBalancerClient loadBalancerClient;
    @Autowired
    RestTemplate restTemplate;
    @Override
    public TokenUtil login(String username, String password, String clientId, String clientSecret) {
        ServiceInstance serviceInstance = loadBalancerClient.choose("user-auth");
        if (serviceInstance == null)
            throw new RuntimeException("没有找到用户服务");
        //令牌url
        String url = serviceInstance.getUri().toString() + "/oauth/token";
        //body
        LinkedMultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type","password");
        body.add("username",username);
        body.add("password",password);
        //headers
        LinkedMultiValueMap<String, String> header = new LinkedMultiValueMap<>();
        String str = clientId+":"+clientSecret;
        byte[] encode = Base64Utils.encode(str.getBytes());
        String s = "Basic " + new String(encode);
        header.add("Authorization",s);

        //处理异常
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler(){
            @Override
            public void handleError(ClientHttpResponse response) throws IOException {
                if (response.getRawStatusCode() != 400 && response.getRawStatusCode() != 401)
                    super.handleError(response);
            }
        });
        Map map = null;
        //发送并返回
        try {
            ResponseEntity<Map> responseEntity = restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(body, header), Map.class);
            map = responseEntity.getBody();
        } catch (RestClientException e) {
            e.printStackTrace();
        }
        if (map == null || map.size() == 0)
            throw new RuntimeException("创建令牌失败");
        for (Object o : map.keySet()) {
            System.out.println(o+""+map.get(o));
        }
        String access_token = map.get("access_token") + "";
        String refresh_token = map.get("refresh_token") + "";
        String jti = map.get("jti") + "";
        return new TokenUtil(access_token,refresh_token,jti);
    }
}
