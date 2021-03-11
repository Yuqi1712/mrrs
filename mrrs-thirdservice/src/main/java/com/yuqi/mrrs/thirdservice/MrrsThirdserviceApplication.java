package com.yuqi.mrrs.thirdservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ApplicationContext;

@EnableDiscoveryClient
@SpringBootApplication(exclude = {RedisAutoConfiguration.class, CacheAutoConfiguration.class})
public class MrrsThirdserviceApplication {

    @Autowired
    ApplicationContext applicationContext;

    public static void main(String[] args) {
        SpringApplication.run(MrrsThirdserviceApplication.class, args);
    }

//    @Override
//    public void run(String... args) throws Exception {
//        String[] names = applicationContext.getBeanDefinitionNames();
//        for(String s:names){
//            System.out.println(s);
//        }
//    }
}
