package com.ujiuye.userauth.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.bootstrap.encrypt.KeyProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import javax.sql.DataSource;
import java.security.KeyPair;

@Configuration
@EnableAuthorizationServer //启用OAuth2授权服务器
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

    @Bean
    public KeyProperties getKeyProperties(){
        return  new KeyProperties();

    }
    @Autowired
    DataSource dataSource;
    //注入密码加密器
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private KeyProperties keyProperties;

    //注入自定义认证对象
    @Autowired
    private UserDetailsService userDetailsService;

    //注入认证管理器
    @Autowired
    private AuthenticationManager authenticationManager;

    //授权服务器端点访问权限验证方式
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.allowFormAuthenticationForClients()//访问服务器端点需要进行客户端身份验证
                .passwordEncoder(passwordEncoder)//设置客户端密码加密机制
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("permitAll()");
    }

    //客户端账号配置
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//        //在内存中创建账号
//        clients.inMemory()
//                // admin，授权码认证、密码认证、客户端认证、简单认证、刷新token
//                .withClient("admin") //账号名称
//                .secret(passwordEncoder.encode("admin"))//密码，要设置加密
//                .resourceIds("dongyimai-user", "dongyimai-goods")//资源编号
//                .scopes("server","app")//作用范围
//                .authorizedGrantTypes("authorization_code", "password", "refresh_token", "client_credentials","implicit")//登录授权模式
//                .redirectUris("http://localhost");//登录成功跳转地址
        clients.jdbc(dataSource).passwordEncoder(passwordEncoder);

    }

    //端点令牌存储方式、关联自定义认证对象、认证管理器
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
//        endpoints.tokenStore(new InMemoryTokenStore()) //令牌存储到内存
//                .authenticationManager(authenticationManager)//认证管理器
//                .userDetailsService(userDetailsService)//自定义认证类
//                .allowedTokenEndpointRequestMethods(HttpMethod.GET,HttpMethod.POST);//允许端点访问方法
        endpoints.tokenStore(new JwtTokenStore(jwtAccessTokenConverter())) //令牌格式jwt
                .accessTokenConverter(jwtAccessTokenConverter())
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService)
                .allowedTokenEndpointRequestMethods(HttpMethod.GET,HttpMethod.POST);


    }

    public KeyPair getKeypair(){
        return  new KeyStoreKeyFactory(keyProperties.getKeyStore().getLocation(), keyProperties.getKeyStore().getSecret().toCharArray())
                .getKeyPair(keyProperties.getKeyStore().getAlias(),keyProperties.getKeyStore().getSecret().toCharArray());
    }

    private  JwtAccessTokenConverter jwtAccessTokenConverter(){
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        jwtAccessTokenConverter.setKeyPair(getKeypair());
        return jwtAccessTokenConverter;
    }
}
