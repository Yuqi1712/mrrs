package com.yuqi.mrrs.auth;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@MapperScan(basePackages = {"com.yuqi.mrrs.auth.dao"})
@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients(basePackages = {"com.yuqi.mrrs.auth.feign"})
public class MrrsAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(MrrsAuthApplication.class, args);
    }

}
