package com.medilabo.patient.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.medilabo.patient.dao.PatientDao;
import com.medilabo.patient.dto.AgeGenreDTO;
import com.medilabo.patient.enums.Genre;
import com.medilabo.patient.exceptions.BirthdayInFutureException;
import com.medilabo.patient.exceptions.PatientNotFoundException;
import com.medilabo.patient.exceptions.UnableAddPatientException;
import com.medilabo.patient.model.Patient;

@ExtendWith(MockitoExtension.class)
class PatientServiceTest {

	@InjectMocks
	private PatientServiceImpl service;

	@Mock
	private PatientDao dao;
	
	private Patient patient1;
	private Patient patient2;
	private Patient patientBadBirthday;
	
	private List<Patient> listPatients = new ArrayList<>();
	
	@BeforeEach
	void setUp() throws Exception {
		patient1 = new Patient(
				0,
				"firstName1", 
				"lastName1", 
				LocalDate.now().minusDays(1),
				Genre.MAN, 
				"", 
				"");

		patient2 = new Patient(
				1,
				"firstName2",
				"lastName2",
				LocalDate.now().minusDays(1),
				Genre.WOMAN,
				"",
				"");

		patientBadBirthday = new Patient(
				2,
				"firstName3",
				"lastName3",
				LocalDate.now().plusDays(1),
				Genre.WOMAN,
				"",
				"");
		
		listPatients.clear();
		listPatients.add(patient1);
		listPatients.add(patient2);
	}
	
	@Test
	void test_WhenListPatients_ThenReturn3Patients() {
		given(dao.findAll()).willReturn(listPatients);

		List<Patient> response = service.listPatients();

		verify(dao, times(1)).findAll();
		assertThat(response.size()).isEqualTo(listPatients.size());
	}

	@Test
	void test_WhenGetPatientById_ThenReturn1Patient() {
		given(dao.findById(anyInt())).willReturn(Optional.of(patient1));

		Optional<Patient> response = service.getPatientById(0);

		verify(dao, times(1)).findById(anyInt());
		assertThat(response.get()).isEqualTo(patient1);
	}

	@Test
	void test_WhenGetPatientById_ThenThrowPatientNotFoundException() {
		assertThrows(PatientNotFoundException.class, () -> {
			given(dao.findById(anyInt())).willReturn(Optional.empty());

			service.getPatientById(0);
		});
		verify(dao, times(1)).findById(anyInt());
	}

	@Test
	void test_WhenSavePatient_ThenReturnPatientSaved() {
		given(dao.save(any())).willReturn(patient1);

		Integer response = service.savePatient(patient1);

		verify(dao, times(1)).save(any());
		assertThat(response).isEqualTo(patient1.getId());
	}

	@Test
	void test_WhenSavePatient_ThenThrowBirthdayInFutureException() {
		assertThrows(BirthdayInFutureException.class, () -> {
			service.savePatient(patientBadBirthday);
		});
		verify(dao, times(0)).save(any());
	}

	@Test
	void test_WhenSavePatient_ThenThrowUnableAddPatientException() {
		assertThrows(UnableAddPatientException.class, () -> {
			given(dao.save(any())).willReturn(null);

			service.savePatient(patient1);
		});
		verify(dao, times(1)).save(any());
	}

	@Test
	void test_WhenUpdatePatient_ThenSavePatient() {
		service.updatePatient(patient1);

		verify(dao, times(1)).save(any());
	}

	@Test
	void test_WhenUpdatePatient_ThenThrowBirthdayInFutureException() {
		assertThrows(BirthdayInFutureException.class, () -> {
			service.updatePatient(patientBadBirthday);
		});
		verify(dao, times(0)).save(any());
	}

	@Test
	void test_GivenGoodId_WhenDeletePatientById_ThenDeletePatient() {
		service.deletePatientById(patient1.getId());

		verify(dao, times(1)).deleteById(anyInt());
	}

	@Test
	void test_GivenBadId_WhenDeletePatientById_ThenNothing() {
		service.deletePatientById(20);

		verify(dao, times(1)).deleteById(anyInt());
	}

	@Test
	void test_WhenGetAgeAndGenreByPatientId_ThenReturnAgeGenreDTO() {
		final AgeGenreDTO dto = new AgeGenreDTO((byte) 0, "M");

		given(dao.findById(anyInt())).willReturn(Optional.of(patient1));

		AgeGenreDTO response = service.getAgeAndGenreByPatientId(0);

		verify(dao, times(1)).findById(anyInt());
		assertThat(response).isEqualTo(dto);
	}

	@Test
	void test_WhenGetAgeAndGenreByPatientId_ThenThrowPatientNotFoundException() {
		assertThrows(PatientNotFoundException.class, () -> {
			given(dao.findById(anyInt())).willReturn(Optional.empty());

			service.getAgeAndGenreByPatientId(0);
		});
		verify(dao, times(1)).findById(anyInt());
	}

	@Test
	void test_GivenGoodId_WhenIsExist_ThenReturnTrue() {
		given(dao.findById(anyInt())).willReturn(Optional.of(patient1));

		boolean response = service.isExist(0);

		verify(dao, times(1)).findById(anyInt());
		assertThat(response).isTrue();
	}

	@Test
	void test_GivenBadId_WhenIsExist_ThenReturnFalse() {
		given(dao.findById(anyInt())).willReturn(Optional.empty());

		boolean response = service.isExist(0);

		verify(dao, times(1)).findById(anyInt());
		assertThat(response).isFalse();
	}

}
