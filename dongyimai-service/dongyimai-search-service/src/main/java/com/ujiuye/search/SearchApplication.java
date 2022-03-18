package com.ujiuye.search;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@EnableFeignClients(basePackages = "com.ujiuye.feign")
@EnableElasticsearchRepositories(basePackages = "com.ujiuye.search.dao")
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
@EnableEurekaClient
public class SearchApplication {
    public  static void main(String[] args) {
        SpringApplication.run(SearchApplication.class);
    }

}