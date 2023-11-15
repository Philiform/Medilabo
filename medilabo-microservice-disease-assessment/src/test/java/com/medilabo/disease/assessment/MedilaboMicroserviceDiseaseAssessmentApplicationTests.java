package com.medilabo.disease.assessment;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = "spring.config.location=classpath:/application.properties")
class MedilaboMicroserviceDiseaseAssessmentApplicationTests {

	@Test
	void contextLoads() {
	}

}
