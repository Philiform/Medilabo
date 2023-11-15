package com.medilabo.disease.assessment.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.medilabo.disease.assessment.service.AssessmentRiskService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// TODO: Auto-generated Javadoc
/**
 * The Class AssessmentRiskController.
 */
@RestController

/**
 * Instantiates a new assessment risk controller.
 *
 * @param service the service
 */
@RequiredArgsConstructor

/** The Constant log. */
@Slf4j
public class AssessmentRiskController {

	/** The service. */
	private final AssessmentRiskService service;
	
	/**
	 * Gets the assessment.
	 *
	 * @param patientId the patient id
	 * @param disease the disease
	 * @return the assessment
	 */
	@GetMapping(value = "/patient/{id}/risk/{disease}")
	public String getAssessment(@PathVariable("id") int patientId, @PathVariable final String disease) {
		log.info("getAssessment(" + patientId + ", " + disease + ")");
		
		return service.getAssessment(patientId, disease);
	}
	
}
