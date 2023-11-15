package com.medilabo.clientui.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.medilabo.clientui.beans.PatientBean;
import com.medilabo.clientui.dto.PatientDTO;
import com.medilabo.clientui.mappers.PatientMapper;
import com.medilabo.clientui.proxies.MicroservicePatientProxy;

import lombok.RequiredArgsConstructor;

// TODO: Auto-generated Javadoc
/**
 * The Class PatientServiceImpl.
 */
@Service

/**
 * Instantiates a new patient service impl.
 *
 * @param proxy the proxy
 * @param patientMapper the patient mapper
 */
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

	/** The proxy. */
	private final MicroservicePatientProxy proxy;
	
	/** The patient mapper. */
	private final PatientMapper patientMapper;
	
	/**
	 * List patients.
	 *
	 * @return the list
	 */
	@Override
	public List<PatientDTO> listPatients() {
		List<PatientBean> patients = proxy.listPatients();

		return patients.stream()
				.map(p -> {
					return patientMapper.fromPatient(p);
				})
				.collect(Collectors.toList());
	}

	/**
	 * Gets the patient by id.
	 *
	 * @param patientId the patient id
	 * @return the patient by id
	 */
	@Override
	public Optional<PatientBean> getPatientById(final Integer patientId) {
		Optional<PatientBean> patient = proxy.getPatientById(patientId);
		
		return patient;
	}

	/**
	 * Save patient.
	 *
	 * @param patient the patient
	 * @return the integer
	 */
	@Override
	public Integer savePatient(final PatientBean patient) {
		return proxy.savePatient(patient);
	}

	/**
	 * Update patient.
	 *
	 * @param patient the patient
	 */
	@Override
	public void updatePatient(final PatientBean patient) {
		proxy.updatePatient(patient);
	}

	/**
	 * Delete patient by patient id.
	 *
	 * @param patientId the patient id
	 */
	@Override
	public void deletePatientByPatientId(final Integer patientId) {
		proxy.deletePatientById(patientId);
	}

	/**
	 * Checks if is birthday valid.
	 *
	 * @param date the date
	 * @return true, if is birthday valid
	 */
	@Override
	public boolean isBirthdayValid(final LocalDate date) {
		return LocalDate.now().isAfter(date); 
	}
}
