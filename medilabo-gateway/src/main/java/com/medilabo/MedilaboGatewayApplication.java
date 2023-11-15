package com.medilabo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.ReactiveDiscoveryClient;
import org.springframework.cloud.gateway.discovery.DiscoveryClientRouteDefinitionLocator;
import org.springframework.cloud.gateway.discovery.DiscoveryLocatorProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MedilaboGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(MedilaboGatewayApplication.class, args);
	}

	@Bean
	DiscoveryClientRouteDefinitionLocator clientRouteDefinitionLocator(
			ReactiveDiscoveryClient client,
			DiscoveryLocatorProperties properties) {
		return new DiscoveryClientRouteDefinitionLocator(client, properties);
	}
}
