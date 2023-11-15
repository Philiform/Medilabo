package com.medilabo.clientui.services;

// TODO: Auto-generated Javadoc
/**
 * The Interface DiseaseAssessmentService.
 */
public interface DiseaseAssessmentService {

	/**
	 * Gets the assessment.
	 *
	 * @param patientId the patient id
	 * @param disease the disease
	 * @return the assessment
	 */
	String getAssessment(final Integer patientId, final String disease);
}
