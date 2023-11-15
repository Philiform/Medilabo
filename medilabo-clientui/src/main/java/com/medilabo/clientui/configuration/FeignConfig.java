package com.medilabo.clientui.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.auth.BasicAuthRequestInterceptor;

@Configuration
public class FeignConfig {

    @Bean
    BasicAuthRequestInterceptor mBasicAuthRequestInterceptor(){
        return  new BasicAuthRequestInterceptor("utilisateur", "mdp");
    }

}
