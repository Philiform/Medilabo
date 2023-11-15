package com.medilabo.clientui.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.medilabo.clientui.proxies.MicroserviceDiseaseAssessmentProxy;
import com.medilabo.clientui.services.DiseaseAssessmentServiceImpl;

@ExtendWith(MockitoExtension.class)
class DiseaseAssessmentServiceTest {

	@InjectMocks
	private DiseaseAssessmentServiceImpl service;

	@Mock
	private MicroserviceDiseaseAssessmentProxy proxy;

	@Test
	void test_WhenGetAssessment_ThenReturnAssessment() {
		given(proxy.getAssessment(anyInt(), anyString())).willReturn("None");

		String response = service.getAssessment(0, "Diabète");

		verify(proxy, times(1)).getAssessment(anyInt(), anyString());
		assertThat(response).isEqualTo("None");
	}

}
