package com.medilabo.patient.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.medilabo.patient.dto.AgeGenreDTO;
import com.medilabo.patient.model.Patient;
import com.medilabo.patient.service.PatientService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// TODO: Auto-generated Javadoc
/**
 * The Class PatientController.
 */
@RestController

/**
 * Instantiates a new patient controller.
 *
 * @param patientService the patient service
 */
@RequiredArgsConstructor

/** The Constant log. */
@Slf4j
public class PatientController {

	/** The patient service. */
	private final PatientService patientService;

	/**
	 * List patients.
	 *
	 * @return the list
	 */
	@GetMapping(value = "/patient/list")
	public List<Patient> listPatients() {	
		log.info("listPatients");
		
		return patientService.listPatients();
	}
	
	/**
	 * Gets the patient by id.
	 *
	 * @param id the id
	 * @return the patient by id
	 */
	@GetMapping(value = "/patient/{id}")
	public Optional<Patient> getPatientById(@PathVariable final Integer id) {
		log.info("getPatientById");
		
		return patientService.getPatientById(id);
	}
	
	/**
	 * Save patient.
	 *
	 * @param patient the patient
	 * @return the integer
	 */
	@PostMapping(value = "/patient/save")
	public Integer savePatient(@RequestBody @Valid final Patient patient) {
		log.info("savePatient");
		
		return patientService.savePatient(patient);
	}
	
	/**
	 * Update patient.
	 *
	 * @param patient the patient
	 */
	@PutMapping(value = "/patient/update")
	public void updatePatient(@RequestBody @Valid final Patient patient) {
		log.info("updatePatient");
		
		patientService.updatePatient(patient);
	}
	
	/**
	 * Delete patient by id.
	 *
	 * @param id the id
	 */
	@DeleteMapping(value = "/patient/{id}/delete")
	public void deletePatientById(@PathVariable final Integer id) {
		log.info("deletePatientById");
		
		patientService.deletePatientById(id);
	}
	
	/**
	 * Gets the age and genre by patient id.
	 *
	 * @param id the id
	 * @return the age and genre by patient id
	 */
	@GetMapping(value = "/patient/{id}/ageAndGenre")
	public AgeGenreDTO getAgeAndGenreByPatientId(@PathVariable final Integer id) {
		log.info("getAgeAndGenreByPatientId");
		
		return patientService.getAgeAndGenreByPatientId(id);
	}
	
	/**
	 * Checks if is exist by patient id.
	 *
	 * @param id the id
	 * @return true, if is exist by patient id
	 */
	@GetMapping(value = "/patient/{id}/isExist")
	public boolean isExistByPatientId(@PathVariable final Integer id) {
		log.info("isExistByPatientId");
		
		return patientService.isExist(id);
	}
	
	/**
	 * Exception handler.
	 *
	 * @param e the e
	 * @return the response entity
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> exceptionHandler(Exception e) {
		log.info("exceptionHandler");
		log.debug(e.getMessage());

		return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
