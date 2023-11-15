package com.medilabo.clientui.proxies;

import java.util.List;
import java.util.Optional;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.medilabo.clientui.beans.PatientBean;

// TODO: Auto-generated Javadoc
/**
 * The Interface MicroservicePatientProxy.
 */
@FeignClient(name = "microservice-patient")
public interface MicroservicePatientProxy {

	/**
	 * List patients.
	 *
	 * @return the list
	 */
	@GetMapping("/patient/list")
	List<PatientBean> listPatients();

	/**
	 * Gets the patient by id.
	 *
	 * @param patientId the patient id
	 * @return the patient by id
	 */
	@GetMapping("/patient/{patientId}")
	Optional<PatientBean> getPatientById(@PathVariable final Integer patientId);

	/**
	 * Save patient.
	 *
	 * @param patient the patient
	 * @return the integer
	 */
	@PostMapping("/patient/save")
	Integer savePatient(@RequestBody final PatientBean patient);
		
	/**
	 * Update patient.
	 *
	 * @param patient the patient
	 */
	@PutMapping("/patient/update")
	void updatePatient(@RequestBody final PatientBean patient);

	/**
	 * Delete patient by id.
	 *
	 * @param patientId the patient id
	 */
	@DeleteMapping("/patient/{patientId}/delete")
	void deletePatientById(@PathVariable final Integer patientId);
	
}
