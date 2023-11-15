package com.medilabo.disease.assessment.service;

// TODO: Auto-generated Javadoc
/**
 * The Interface AssessmentRiskService.
 */
public interface AssessmentRiskService {

	/**
	 * Gets the assessment.
	 *
	 * @param patientId the patient id
	 * @param disease the disease
	 * @return the assessment
	 */
	String getAssessment(final Integer patientId, final String disease);
}
