package com.yuqi.mrrs.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class MrrsGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(MrrsGatewayApplication.class, args);
    }

}
