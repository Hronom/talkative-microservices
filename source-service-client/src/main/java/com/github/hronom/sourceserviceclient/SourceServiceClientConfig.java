package com.github.hronom.sourceserviceclient;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.Request;

@Configuration
public class SourceServiceClientConfig {

    @Bean
    public Request.Options options() {
        return new Request.Options(100, 2500);
    }
}
