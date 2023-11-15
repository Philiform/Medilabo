package com.medilabo.disease.assessment.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import com.medilabo.disease.assessment.service.AssessmentRiskService;

@WebMvcTest(controllers = AssessmentRiskController.class)
@TestPropertySource(properties = "spring.config.location=classpath:/application.properties")
class AssessmentRiskControllerTest {

	@Autowired
	private AssessmentRiskController controller;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	public AssessmentRiskService service;
	
	@Test
	public void testGivenController_ThenReturnHttpStatusNotNull() throws Exception {
		assertThat(controller).isNotNull();
	}

	@Test
	public void testGivenPasDePages_ThenReturnHttpStatusNotFound() throws Exception {
		mockMvc
			.perform(get("/pasDePages"))
			.andExpect(status().isNotFound());
	}

	@Test
	void test_GivenBadId_WhenGetAssessment_ThenReturnEmpty() throws Exception {
		given(service.getAssessment(anyInt(), anyString())).willReturn("");

		mockMvc
			.perform(get("/patient/{id}/risk/{disease}", 0, "disease"))
			.andExpect(content().string(""))
			.andExpect(status().isOk());
		
		verify(service, times(1)).getAssessment(anyInt(), anyString());
	}

	@Test
	void test_GivenGoodId_WhenGetAssessment_ThenReturnNone() throws Exception {
		given(service.getAssessment(anyInt(), anyString())).willReturn("None");

		mockMvc
			.perform(get("/patient/{id}/risk/{disease}", 0, "disease"))
			.andExpect(content().string("None"))
			.andExpect(status().isOk());
		
		verify(service, times(1)).getAssessment(anyInt(), anyString());
	}

	@Test
	void test_GivenGoodId_WhenGetAssessment_ThenReturnBorderline() throws Exception {
		given(service.getAssessment(anyInt(), anyString())).willReturn("Borderline");

		mockMvc
			.perform(get("/patient/{id}/risk/{disease}", 0, "disease"))
			.andExpect(content().string("Borderline"))
			.andExpect(status().isOk());
		
		verify(service, times(1)).getAssessment(anyInt(), anyString());
	}

	@Test
	void test_GivenGoodId_WhenGetAssessment_ThenReturnInDanger() throws Exception {
		given(service.getAssessment(anyInt(), anyString())).willReturn("In Danger");

		mockMvc
			.perform(get("/patient/{id}/risk/{disease}", 0, "disease"))
			.andExpect(content().string("In Danger"))
			.andExpect(status().isOk());
		
		verify(service, times(1)).getAssessment(anyInt(), anyString());
	}

	@Test
	void test_GivenGoodId_WhenGetAssessment_ThenReturnEarlyOnset() throws Exception {
		given(service.getAssessment(anyInt(), anyString())).willReturn("Early onset");

		mockMvc
			.perform(get("/patient/{id}/risk/{disease}", 0, "disease"))
			.andExpect(content().string("Early onset"))
			.andExpect(status().isOk());
		
		verify(service, times(1)).getAssessment(anyInt(), anyString());
	}

}
