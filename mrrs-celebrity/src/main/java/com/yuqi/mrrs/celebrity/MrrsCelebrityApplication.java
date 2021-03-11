package com.yuqi.mrrs.celebrity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class MrrsCelebrityApplication {

    public static void main(String[] args) {
        SpringApplication.run(MrrsCelebrityApplication.class, args);
    }

}
