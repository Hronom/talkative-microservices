package com.github.hronom.sourceserviceclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import static java.util.Collections.singletonList;
//import org.springframework.integration.annotation.IntegrationComponentScan;

//@IntegrationComponentScan
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
@Configuration
public class SourceServiceClientApplication {
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setInterceptors(singletonList((ClientHttpRequestInterceptor) (request, body, execution) -> {
            request.getHeaders().add("X-Forwarded-Host", request.getURI().getHost());
            return execution.execute(request, body);

        }));
        return restTemplate;
    }

    public static void main(String[] args) {
        SpringApplication.run(SourceServiceClientApplication.class, args);
    }
}
