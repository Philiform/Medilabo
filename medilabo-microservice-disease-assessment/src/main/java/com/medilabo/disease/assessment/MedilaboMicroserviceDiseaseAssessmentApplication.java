package com.medilabo.disease.assessment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients("com.medilabo.disease")
@EnableDiscoveryClient
public class MedilaboMicroserviceDiseaseAssessmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(MedilaboMicroserviceDiseaseAssessmentApplication.class, args);
	}

}
