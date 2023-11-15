package com.medilabo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class MedilaboEurekaServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MedilaboEurekaServerApplication.class, args);
	}

}
