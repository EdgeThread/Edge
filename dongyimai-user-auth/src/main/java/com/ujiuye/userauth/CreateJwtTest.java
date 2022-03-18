package com.ujiuye.userauth;

import com.alibaba.fastjson.JSON;
import org.bouncycastle.util.encoders.Base64Encoder;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaSigner;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.rsa.crypto.KeyStoreKeyFactory;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.util.HashMap;
import java.util.Map;

public class CreateJwtTest {
    //证书文件路径(放在resources目录下)
    String key_location="dongyimai.jks";
    //秘钥库密码
    String key_password="dongyimai";
    //秘钥密码
    String keypwd = "dongyimai";
    //秘钥别名
    String alias = "dongyimai";
    @Test
    public void test(){


        ClassPathResource resource = new ClassPathResource(key_location);
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(resource, key_password.toCharArray());
        KeyPair keyPair = keyStoreKeyFactory.getKeyPair(alias, keypwd.toCharArray());
        RSAPrivateKey aPrivate = (RSAPrivateKey) keyPair.getPrivate();
        Map<String,Object> map = new HashMap<>();
        map.put("id","1");
        map.put("name","ujiuye");
        map.put("roles","role_VIP,role_admin");
        Jwt encode = JwtHelper.encode(JSON.toJSONString(map), new RsaSigner(aPrivate));
        System.out.println(encode.getEncoded());
    }

    @Test
    public void test2(){

    String token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJyb2xlcyI6InJvbGVfVklQLHJvbGVfYWRtaW4iLCJuYW1lIjoidWppdXllIiwiaWQiOiIxIn0.hZldlgGpK3eXrJdi7StuxoY3CYt2ugjkrD2lCOGufw_AOACGHkjcdm3TgA-Nf8PWmhuw79RAUMysI39AEvWN9tukEMSGtFgoUvCWyAW4hoKC1nYd5Q1DovNd8eNfKFLByIDR2sHGQobfzfgA0r2qAggBolJqW-fsSnSYcVqxbyPKW5WRdeTMg5es-qAGUydFja7b-3XU0NlYEfhxLAmHe_FMYab5nB9SorEfqGYzeMCig1dC7BUrGPLPupsplfscKIYm9puY_wS-VFm6zPTEXu4Wb82nFqlJNsX3DNxNYCMqFUJ1ZkrJlYQoeQOgDDuvUGmHyH2fn_RhFikqMWCT2Q";
    String publicKey = "-----BEGIN PUBLIC KEY-----\n" +
            "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAqjgYZR8mGqH4uJMvwoDa" +
            "ypcx78+KmdJVRjoO/8qW5ed0lu120018sLha+Wdc8GStr6xkEnZo939bs6HBTS10" +
            "4+8iSe287G5JMcnLUEH1+wyGh0RznLkRBD+hYE08Or81LnhCmymccgFZqLb9l+um" +
            "qCESR7V2xkSlSTu4jk2FXPFJT0+owEP3qImkQ0eAs3lr0aoTwF6MMw1+GULFMZMz" +
            "k30CcHX/HqoVlPANx7ZVFrWWMTFT3B7mOEKG4fp5Hl5JcuXwkUxW78hp5fVsExw7" +
            "z2yPn88FHcFsqnu/rNpq1DZE9RCD1yYXp9RojGKE0biP1p+2XTlTtIcBLK+HNySZ" +
            "HwIDAQAB" +
            "-----END PUBLIC KEY-----";
        System.out.println(publicKey);
        Jwt jwt = JwtHelper.decodeAndVerify(token, new RsaVerifier(publicKey));
        System.out.println(jwt.getClaims());
    }

}
