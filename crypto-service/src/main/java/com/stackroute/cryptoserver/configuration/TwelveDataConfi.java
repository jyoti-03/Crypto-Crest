package com.stackroute.cryptoserver.configuration;


import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class TwelveDataConfi {
    @Bean
    public RestTemplate restTemplateBuild(RestTemplateBuilder restTemplateBuilder){
        return restTemplateBuilder.build();
    }
}



















