package com.medilabo.patient.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.medilabo.patient.dto.AgeGenreDTO;
import com.medilabo.patient.enums.Genre;
import com.medilabo.patient.exceptions.BirthdayInFutureException;
import com.medilabo.patient.exceptions.PatientNotFoundException;
import com.medilabo.patient.exceptions.UnableAddPatientException;
import com.medilabo.patient.model.Patient;
import com.medilabo.patient.service.PatientService;

@WebMvcTest(controllers = PatientController.class)
@TestPropertySource(properties = "spring.config.location=classpath:/application.properties")
class PatientControllerTest {

	@Autowired
	private PatientController controller;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	public PatientService service;
	
	private Patient patient1;
	private Patient patient2;
	private Patient patient3;
	
	private List<Patient> listPatients = new ArrayList<>();

	@BeforeEach
	void setUp() throws Exception {
		patient1 = new Patient(
				0,
				"firstName1", 
				"lastName1", 
				LocalDate.now(), 
				Genre.MAN, 
				"", 
				"");

		patient2 = new Patient(
				1,
				"firstName2",
				"lastName2",
				LocalDate.now(),
				Genre.WOMAN,
				"",
				"");

		patient3 = new Patient(
				2,
				"firstName3",
				"lastName3",
				LocalDate.now(),
				Genre.WOMAN,
				"",
				"");
		
		listPatients.clear();
		listPatients.add(patient1);
		listPatients.add(patient2);
		listPatients.add(patient3);
	}
	
	@Test
	public void testGivenController_ThenReturnHttpStatusNotNull() throws Exception {
		assertThat(controller).isNotNull();
	}

	@Test
	public void testGivenPasDePages_ThenReturnHttpStatusNotFound() throws Exception {
		mockMvc
			.perform(get("/pasDePages"))
			.andExpect(status().isNotFound());
	}

	@Test
	void test_WhenListPatients_ThenReturnHttpStatusOk() throws Exception {
		given(service.listPatients()).willReturn(listPatients);

		mockMvc
			.perform(get("/patient/list"))
			.andExpect(status().isOk());
		
		verify(service, times(1)).listPatients();
	}

	@Test
	void test_WhenListPatients_ThenReturnHttpStatusInternalServerError() throws Exception {
		given(service.listPatients()).willThrow(new PatientNotFoundException(""));

		mockMvc
			.perform(get("/patient/list"))
			.andExpect(status().isInternalServerError());
		
		verify(service, times(1)).listPatients();
	}

	@Test
	void test_WhenGetPatientById_ThenReturnHttpStatusOk() throws Exception {
		given(service.getPatientById(anyInt())).willReturn(Optional.of(patient1));

		mockMvc
			.perform(get("/patient/{id}", 0))
			.andExpect(status().isOk());
		
		verify(service, times(1)).getPatientById(anyInt());
	}

	@Test
	void test_WhenGetPatientById_ThenReturnHttpStatusInternalServerError() throws Exception {
		given(service.getPatientById(anyInt())).willThrow(new PatientNotFoundException(""));

		mockMvc
			.perform(get("/patient/{id}", 0))
			.andExpect(status().isInternalServerError());
		
		verify(service, times(1)).getPatientById(anyInt());
	}

	@Test
	void test_WhenSavePatient_ThenReturnHttpStatusOk() throws Exception {
		given(service.savePatient(any(Patient.class))).willReturn(patient1.getId());

		mockMvc
			.perform(post("/patient/save")
				.content(new ObjectMapper().writeValueAsString(patient1))
			    .contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
		
		verify(service, times(1)).savePatient(any());
	}

	@Test
	void test_GivenBadBirthday_WhenSavePatient_ThenReturnHttpStatusInternalServerError() throws Exception {
		given(service.savePatient(any(Patient.class))).willThrow(new BirthdayInFutureException(""));

		mockMvc
		.perform(post("/patient/save")
				.content(new ObjectMapper().writeValueAsString(patient1))
			    .contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isInternalServerError());
		
		verify(service, times(1)).savePatient(any());
	}

	@Test
	void test_WhenSavePatient_ThenReturnHttpStatusInternalServerError() throws Exception {
		given(service.savePatient(any(Patient.class))).willThrow(new UnableAddPatientException(""));

		mockMvc
		.perform(post("/patient/save")
				.content(new ObjectMapper().writeValueAsString(patient1))
			    .contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isInternalServerError());
		
		verify(service, times(1)).savePatient(any());
	}

	@Test
	void test_WhenUpdatePatient_ThenReturnHttpStatusOk() throws Exception {
		mockMvc
			.perform(put("/patient/update")
				.content(new ObjectMapper().writeValueAsString(patient1))
			    .contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
		
		verify(service, times(1)).updatePatient(any(Patient.class));
	}

	@Test
	void test_WhenDeletePatient_ThenReturnHttpStatusOk() throws Exception {
		mockMvc
			.perform(delete("/patient/{id}/delete", 0)
			    .contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
		
		verify(service, times(1)).deletePatientById(anyInt());
	}

	@Test
	void test_WhenGetAgeAndGenreByPatientId_ThenReturnHttpStatusOk() throws Exception {
		final AgeGenreDTO dto = new AgeGenreDTO((byte) 0, "M");

		given(service.getAgeAndGenreByPatientId(anyInt())).willReturn(dto);

		mockMvc
			.perform(get("/patient/{id}/ageAndGenre", 0))
			.andExpect(status().isOk());
		
		verify(service, times(1)).getAgeAndGenreByPatientId(anyInt());
	}

	@Test
	void test_WhenGetAgeAndGenreByPatientId_ThenReturnHttpStatusInternalServerError() throws Exception {
		given(service.getAgeAndGenreByPatientId(anyInt())).willThrow(new PatientNotFoundException(""));

		mockMvc
			.perform(get("/patient/{id}/ageAndGenre", 0))
			.andExpect(status().isInternalServerError());
		
		verify(service, times(1)).getAgeAndGenreByPatientId(anyInt());
	}

	@Test
	void test_GIvenGoodId_WhenIsExistByPatientId_ThenReturnTrue() throws Exception {
		given(service.isExist(anyInt())).willReturn(true);

		mockMvc
			.perform(get("/patient/{id}/isExist", 0))
			.andExpect(content().string("true"))
			.andExpect(status().isOk());
		
		verify(service, times(1)).isExist(anyInt());
	}

	@Test
	void test_GIvenBadId_WhenIsExistByPatientId_ThenReturnFalse() throws Exception {
		given(service.isExist(anyInt())).willReturn(false);

		mockMvc
			.perform(get("/patient/{id}/isExist", 0))
			.andExpect(content().string("false"))
			.andExpect(status().isOk());
		
		verify(service, times(1)).isExist(anyInt());
	}

}
