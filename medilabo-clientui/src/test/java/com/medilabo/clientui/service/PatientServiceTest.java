package com.medilabo.clientui.service;

import static org.assertj.core.api.Assertions.assertThat;
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

import com.medilabo.clientui.beans.PatientBean;
import com.medilabo.clientui.dto.PatientDTO;
import com.medilabo.clientui.enums.Genre;
import com.medilabo.clientui.mappers.PatientMapper;
import com.medilabo.clientui.proxies.MicroservicePatientProxy;
import com.medilabo.clientui.services.PatientServiceImpl;

@ExtendWith(MockitoExtension.class)
class PatientServiceTest {

	@InjectMocks
	private PatientServiceImpl service;

	@Mock
	private MicroservicePatientProxy proxy;
	
	@Mock
	private PatientMapper patientMapper;

	private PatientBean patient1;
	private PatientBean patient2;
	private PatientBean patient3;
	
	private List<PatientBean> listPatients = new ArrayList<>();
	
	@BeforeEach
	void setUp() throws Exception {
		patient1 = new PatientBean(
				0,
				"firstName1", 
				"lastName1", 
				LocalDate.now(), 
				Genre.MAN, 
				"", 
				"");

		patient2 = new PatientBean(
				1,
				"firstName2",
				"lastName2",
				LocalDate.now(),
				Genre.WOMAN,
				"",
				"");

		patient3 = new PatientBean(
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
	void test_WhenListPatients_ThenReturn3Patients() {
		given(proxy.listPatients()).willReturn(listPatients);
		given(patientMapper.fromPatient(any(PatientBean.class))).willReturn(new PatientDTO());

		List<PatientDTO> response = service.listPatients();

		verify(proxy, times(1)).listPatients();
		verify(patientMapper, times(3)).fromPatient(any(PatientBean.class));
		assertThat(response.size()).isEqualTo(listPatients.size());
	}

	@Test
	void test_WhenGetPatientById_ThenReturn1Patient() {
		given(proxy.getPatientById(anyInt())).willReturn(Optional.of(patient1));

		Optional<PatientBean> response = service.getPatientById(0);

		verify(proxy, times(1)).getPatientById(anyInt());
		assertThat(response.get()).isEqualTo(patient1);
	}

	@Test
	void test_WhenSavePatient_ThenReturnReturn0() {
		given(proxy.savePatient(any(PatientBean.class))).willReturn(0);

		Integer response = service.savePatient(patient1);

		verify(proxy, times(1)).savePatient(any(PatientBean.class));
		assertThat(response).isEqualTo(0);
	}

	@Test
	void test_WhenUpdatePatient_ThenSavePatient() {
		service.updatePatient(patient1);

		verify(proxy, times(1)).updatePatient(any(PatientBean.class));
	}

	@Test
	void test_WhenDeletePatient_ThenDeletePatient() {
		service.deletePatientByPatientId(0);

		verify(proxy, times(1)).deletePatientById(anyInt());
	}

	@Test
	void test_GivenGoodBirthday_WhenIsBirthdayValid_ThenReturnTrue() {
		service.isBirthdayValid(LocalDate.now().minusDays(1));
	}

	@Test
	void test_GivenBadBirthday_WhenIsBirthdayValid_ThenReturnFalse() {
		service.isBirthdayValid(LocalDate.now().plusDays(1));
	}

}
