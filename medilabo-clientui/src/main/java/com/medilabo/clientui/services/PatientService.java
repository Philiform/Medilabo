package com.medilabo.clientui.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.medilabo.clientui.beans.PatientBean;
import com.medilabo.clientui.dto.PatientDTO;

// TODO: Auto-generated Javadoc
/**
 * The Interface PatientService.
 */
public interface PatientService {

	/**
	 * List patients.
	 *
	 * @return the list
	 */
	List<PatientDTO> listPatients();
	
	/**
	 * Gets the patient by id.
	 *
	 * @param patientId the patient id
	 * @return the patient by id
	 */
	Optional<PatientBean> getPatientById(final Integer patientId);
	
	/**
	 * Save patient.
	 *
	 * @param patient the patient
	 * @return the integer
	 */
	Integer savePatient(final PatientBean patient);
	
	/**
	 * Update patient.
	 *
	 * @param patient the patient
	 */
	void updatePatient(final PatientBean patient);
	
	/**
	 * Delete patient by patient id.
	 *
	 * @param patientId the patient id
	 */
	void deletePatientByPatientId(final Integer patientId);
	
	/**
	 * Checks if is birthday valid.
	 *
	 * @param date the date
	 * @return true, if is birthday valid
	 */
	boolean isBirthdayValid(final LocalDate date);
}
