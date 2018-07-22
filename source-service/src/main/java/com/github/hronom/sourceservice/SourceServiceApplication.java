package com.github.hronom.sourceservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@EnableDiscoveryClient
@SpringBootApplication
@EnableMongoAuditing
public class SourceServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(SourceServiceApplication.class, args);
    }
}
