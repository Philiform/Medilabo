package com.medilabo.notes;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = "spring.config.location=classpath:/application.properties")
class MedilaboMicroserviceNotesPatientsApplicationTests {

	@Test
	void contextLoads() {
	}

}
