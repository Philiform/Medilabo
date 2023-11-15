package com.medilabo.disease.assessment.proxies;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.medilabo.disease.assessment.beans.AgeGenreDTOBean;

// TODO: Auto-generated Javadoc
/**
 * The Interface MicroservicePatientProxy.
 */
@FeignClient(name = "microservice-patient")
public interface MicroservicePatientProxy {

	/**
	 * Gets the age and genre by patient id.
	 *
	 * @param patientId the patient id
	 * @return the age and genre by patient id
	 */
	@GetMapping(value = "/patient/{patientId}/ageAndGenre")
	AgeGenreDTOBean getAgeAndGenreByPatientId(@PathVariable final Integer patientId);

	/**
	 * Checks if is exist by patient id.
	 *
	 * @param patientId the patient id
	 * @return true, if is exist by patient id
	 */
	@GetMapping(value = "/patient/{patientId}/isExist")
	boolean isExistByPatientId(@PathVariable final Integer patientId);

}
