package com.medilabo.clientui.proxies;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// TODO: Auto-generated Javadoc
/**
 * The Interface MicroserviceDiseaseAssessmentProxy.
 */
@FeignClient(name = "microservice-disease-assessment")
public interface MicroserviceDiseaseAssessmentProxy {

	/**
	 * Gets the assessment.
	 *
	 * @param patientId the patient id
	 * @param disease the disease
	 * @return the assessment
	 */
	@GetMapping(value = "/patient/{id}/risk/{disease}")
	public String getAssessment(@PathVariable("id") final Integer patientId, @PathVariable final String disease);
	
}
