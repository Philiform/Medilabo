package com.medilabo.patient.service;

import java.util.List;
import java.util.Optional;

import com.medilabo.patient.dto.AgeGenreDTO;
import com.medilabo.patient.model.Patient;

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
	List<Patient> listPatients();
	
	/**
	 * Gets the patient by id.
	 *
	 * @param id the id
	 * @return the patient by id
	 */
	Optional<Patient> getPatientById(final Integer id);
	
	/**
	 * Save patient.
	 *
	 * @param patient the patient
	 * @return the integer
	 */
	Integer savePatient(final Patient patient);
	
	/**
	 * Update patient.
	 *
	 * @param patient the patient
	 */
	void updatePatient(final Patient patient);
	
	/**
	 * Delete patient by id.
	 *
	 * @param id the id
	 */
	void deletePatientById(final Integer id);
	
	/**
	 * Gets the age and genre by patient id.
	 *
	 * @param id the id
	 * @return the age and genre by patient id
	 */
	AgeGenreDTO getAgeAndGenreByPatientId(final Integer id);
	
	/**
	 * Checks if is exist.
	 *
	 * @param id the id
	 * @return true, if is exist
	 */
	boolean isExist(final Integer id);

}
