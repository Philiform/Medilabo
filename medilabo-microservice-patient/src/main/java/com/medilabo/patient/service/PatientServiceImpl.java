package com.medilabo.patient.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.medilabo.patient.dao.PatientDao;
import com.medilabo.patient.dto.AgeGenreDTO;
import com.medilabo.patient.exceptions.BirthdayInFutureException;
import com.medilabo.patient.exceptions.PatientNotFoundException;
import com.medilabo.patient.exceptions.UnableAddPatientException;
import com.medilabo.patient.model.Patient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// TODO: Auto-generated Javadoc
/**
 * The Class PatientServiceImpl.
 */
@Service

/**
 * Instantiates a new patient service impl.
 *
 * @param patientDao the patient dao
 */
@RequiredArgsConstructor
@Transactional

/** The Constant log. */
@Slf4j
public class PatientServiceImpl implements PatientService {

	/** The patient dao. */
	private final PatientDao patientDao;

	/**
	 * List patients.
	 *
	 * @return the list
	 */
	@Override
	public List<Patient> listPatients() {
		return patientDao.findAll();
	}

	/**
	 * Gets the patient by id.
	 *
	 * @param id the id
	 * @return the patient by id
	 */
	@Override
	public Optional<Patient> getPatientById(final Integer id) {
		Optional<Patient> patient = patientDao.findById(id);
		
		if(!patient.isPresent()) throw new PatientNotFoundException("Patient not found.");
		
		return patient;
	}

	/**
	 * Save patient.
	 *
	 * @param patient the patient
	 * @return the integer
	 */
	@Override
	public Integer savePatient(final Patient patient) {		
		if(!isBirthdayValid(patient.getBirthday())) throw new BirthdayInFutureException("Date of birth in the future");

		Patient newPatient = patientDao.save(patient);
		
		if(newPatient == null) throw new UnableAddPatientException("Unable to add patient");
		
		return newPatient.getId();
	}

	/**
	 * Update patient.
	 *
	 * @param patient the patient
	 */
	@Override
	public void updatePatient(final Patient patient) {
		if(!isBirthdayValid(patient.getBirthday())) throw new BirthdayInFutureException("Date of birth in the future");
		
		patientDao.save(patient);
	}

	/**
	 * Delete patient by id.
	 *
	 * @param id the id
	 */
	@Override
	public void deletePatientById(final Integer id) {
		log.debug("deletePatientById");
		patientDao.deleteById(id);
	}

	/**
	 * Gets the age and genre by patient id.
	 *
	 * @param id the id
	 * @return the age and genre by patient id
	 */
	@Override
	public AgeGenreDTO getAgeAndGenreByPatientId(final Integer id) {
		Optional<Patient> patient = patientDao.findById(id);
		
		if(!patient.isPresent()) throw new PatientNotFoundException("Patient not found.");
		
		AgeGenreDTO dto = new AgeGenreDTO();
		dto.setAge((byte) Period.between(patient.get().getBirthday(), LocalDate.now()).getYears());
		dto.setGenre(patient.get().getGenre());

		return dto;
	}

	/**
	 * Checks if is exist.
	 *
	 * @param id the id
	 * @return true, if is exist
	 */
	@Override
	public boolean isExist(final Integer id) {
		Optional<Patient> patient = patientDao.findById(id);

		return patient.isPresent();
	}

	/**
	 * Checks if is birthday valid.
	 *
	 * @param date the date
	 * @return true, if is birthday valid
	 */
	private boolean isBirthdayValid(final LocalDate date) {
		return LocalDate.now().isAfter(date); 
	}

}
