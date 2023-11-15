package com.medilabo.clientui.services;

import org.springframework.stereotype.Service;

import com.medilabo.clientui.proxies.MicroserviceDiseaseAssessmentProxy;

import lombok.RequiredArgsConstructor;

// TODO: Auto-generated Javadoc
/**
 * The Class DiseaseAssessmentServiceImpl.
 */
@Service

/**
 * Instantiates a new disease assessment service impl.
 *
 * @param proxy the proxy
 */
@RequiredArgsConstructor
public class DiseaseAssessmentServiceImpl implements DiseaseAssessmentService {

	/** The proxy. */
	private final MicroserviceDiseaseAssessmentProxy proxy;
	
	/**
	 * Gets the assessment.
	 *
	 * @param patientId the patient id
	 * @param disease the disease
	 * @return the assessment
	 */
	@Override
	public String getAssessment(final Integer patientId, final String disease) {
		return proxy.getAssessment(patientId, disease);
	}

}
