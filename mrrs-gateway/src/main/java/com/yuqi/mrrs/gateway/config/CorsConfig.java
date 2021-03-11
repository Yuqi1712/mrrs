package com.yuqi.mrrs.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

@Configuration
public class CorsConfig {

    @Bean
    public CorsWebFilter corsWebFilter(){
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        CorsConfiguration corsConfiguration = new CorsConfiguration();

        //1、配置跨域
        corsConfiguration.addAllowedHeader("*");    //允许任意字段
        corsConfiguration.addAllowedMethod("*");    //允许任意方式的请求
        corsConfiguration.addAllowedOrigin("*");    //允许任意来源
        corsConfiguration.setAllowCredentials(true);   //允许cookie

        source.registerCorsConfiguration("/**",corsConfiguration);    //任意路径都需要跨域配置
        return new CorsWebFilter(source);
    }

}
