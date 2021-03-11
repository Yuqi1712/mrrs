package com.yuqi.mrrs.subject;

import com.yuqi.yuqi.exception.MrrsExceptionHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@EnableFeignClients(basePackages = {"com.yuqi.mrrs.subject.feign"})
@EnableHystrix
//@Import(MrrsExceptionHandler.class)
public class MrrsSubjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(MrrsSubjectApplication.class, args);
    }

}
