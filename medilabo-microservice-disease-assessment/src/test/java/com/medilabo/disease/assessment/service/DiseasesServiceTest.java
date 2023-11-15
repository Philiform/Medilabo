package com.medilabo.disease.assessment.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.medilabo.disease.assessment.beans.AgeGenreDTOBean;
import com.medilabo.disease.assessment.beans.NoteBean;
import com.medilabo.disease.assessment.enums.Genre;
import com.medilabo.disease.assessment.proxies.MicroserviceNotePatientProxy;
import com.medilabo.disease.assessment.proxies.MicroservicePatientProxy;

@ExtendWith(MockitoExtension.class)
class DiseasesServiceTest {

	@InjectMocks
	private AssessmentRiskServiceImpl assessmentRiskService;

	@Mock
	private DiseasesService diseasesService;

	@Mock
	private MicroservicePatientProxy patientProxy;

	@Mock
	private MicroserviceNotePatientProxy notePatientProxy;
	
	private static NoteBean note1;

	private static AgeGenreDTOBean man15;
	private static AgeGenreDTOBean woman27;
	private static AgeGenreDTOBean man40;
	private static AgeGenreDTOBean woman50;
	
	private static List<NoteBean> list1NoteWithoutSearchTerm = new ArrayList<NoteBean>();
	private static List<NoteBean> list1Note = new ArrayList<NoteBean>();
	private static List<NoteBean> list3Notes = new ArrayList<NoteBean>();
	private static List<NoteBean> list6Notes = new ArrayList<NoteBean>();
	private static List<NoteBean> list9Notes = new ArrayList<NoteBean>();

	private static List<String> termsToSearch = new ArrayList<>();
	
	@BeforeAll
	static void setUp() throws Exception {
		note1 = new NoteBean(UUID.randomUUID(), LocalDate.now(), "note1");
		
		man15 = new AgeGenreDTOBean((byte) 15, Genre.MAN);
		woman27 = new AgeGenreDTOBean((byte) 27, Genre.WOMAN);
		man40 = new AgeGenreDTOBean((byte) 40, Genre.MAN);
		woman50 = new AgeGenreDTOBean((byte) 50, Genre.WOMAN);

		list1NoteWithoutSearchTerm.add(new NoteBean(UUID.randomUUID(), LocalDate.now(), "note without search term"));
		
		list1Note.add(note1);

		list3Notes.addAll(List.of(note1, note1, note1));
		
		list6Notes.addAll(list3Notes);
		list6Notes.addAll(list3Notes);
		
		list9Notes.addAll(list3Notes);
		list9Notes.addAll(list6Notes);

		termsToSearch.add("note1");
	}

	@Test
	void test_GivenBadId_WhenGetAssessment_ThenReturnResponseEmpty() {
		given(patientProxy.isExistByPatientId(anyInt())).willReturn(false);

		String response = assessmentRiskService.getAssessment(0, "");

		verify(patientProxy, times(1)).isExistByPatientId(anyInt());
		assertThat(response).isEqualTo("");
	}

	// LIST SIZE = 0
	// None
	@Test
	void test_Given0Note_WhenGetAssessment_ThenReturnResponseNone() {
		given(patientProxy.isExistByPatientId(anyInt())).willReturn(true);
		given(notePatientProxy.listNotesByPatientId(anyInt())).willReturn(new ArrayList<>());

		String response = assessmentRiskService.getAssessment(0, "");

		verify(patientProxy, times(1)).isExistByPatientId(anyInt());
		verify(notePatientProxy, times(1)).listNotesByPatientId(anyInt());
		assertThat(response).isEqualTo("None");
	}

	// TESTS AGE >= 30
	// None
	@Test
	void test_GivenMan40List1NoteWithoutSearchTerm_WhenGetAssessment_ThenReturnResponseNone() {
		given(patientProxy.isExistByPatientId(anyInt())).willReturn(true);
		given(notePatientProxy.listNotesByPatientId(anyInt())).willReturn(list1NoteWithoutSearchTerm);
		given(patientProxy.getAgeAndGenreByPatientId(anyInt())).willReturn(man40);
		given(diseasesService.getAllTermsByDisease(anyString())).willReturn(termsToSearch);

		String response = assessmentRiskService.getAssessment(0, "");

		verify(patientProxy, times(1)).isExistByPatientId(anyInt());
		verify(notePatientProxy, times(1)).listNotesByPatientId(anyInt());
		verify(patientProxy, times(1)).getAgeAndGenreByPatientId(anyInt());
		verify(diseasesService, times(1)).getAllTermsByDisease(anyString());
		assertThat(response).isEqualTo("None");
	}

	@Test
	void test_GivenMan40List1Note_WhenGetAssessment_ThenReturnResponseNone() {
		given(patientProxy.isExistByPatientId(anyInt())).willReturn(true);
		given(notePatientProxy.listNotesByPatientId(anyInt())).willReturn(list1Note);
		given(patientProxy.getAgeAndGenreByPatientId(anyInt())).willReturn(man40);
		given(diseasesService.getAllTermsByDisease(anyString())).willReturn(termsToSearch);

		String response = assessmentRiskService.getAssessment(0, "");

		verify(patientProxy, times(1)).isExistByPatientId(anyInt());
		verify(notePatientProxy, times(1)).listNotesByPatientId(anyInt());
		verify(patientProxy, times(1)).getAgeAndGenreByPatientId(anyInt());
		verify(diseasesService, times(1)).getAllTermsByDisease(anyString());
		assertThat(response).isEqualTo("None");
	}

	// Borderline
	@Test
	void test_GivenWonan50List3Notes_WhenGetAssessment_ThenReturnResponseBorderline() {
		given(patientProxy.isExistByPatientId(anyInt())).willReturn(true);
		given(notePatientProxy.listNotesByPatientId(anyInt())).willReturn(list3Notes);
		given(patientProxy.getAgeAndGenreByPatientId(anyInt())).willReturn(woman50);
		given(diseasesService.getAllTermsByDisease(anyString())).willReturn(termsToSearch);

		String response = assessmentRiskService.getAssessment(0, "");

		verify(patientProxy, times(1)).isExistByPatientId(anyInt());
		verify(notePatientProxy, times(1)).listNotesByPatientId(anyInt());
		verify(patientProxy, times(1)).getAgeAndGenreByPatientId(anyInt());
		verify(diseasesService, times(1)).getAllTermsByDisease(anyString());
		assertThat(response).isEqualTo("Borderline");
	}

	// In Danger
	@Test
	void test_GivenWoman50List6Notes_WhenGetAssessment_ThenReturnResponseInDanger() {
		given(patientProxy.isExistByPatientId(anyInt())).willReturn(true);
		given(notePatientProxy.listNotesByPatientId(anyInt())).willReturn(list6Notes);
		given(patientProxy.getAgeAndGenreByPatientId(anyInt())).willReturn(woman50);
		given(diseasesService.getAllTermsByDisease(anyString())).willReturn(termsToSearch);

		String response = assessmentRiskService.getAssessment(0, "");

		verify(patientProxy, times(1)).isExistByPatientId(anyInt());
		verify(notePatientProxy, times(1)).listNotesByPatientId(anyInt());
		verify(patientProxy, times(1)).getAgeAndGenreByPatientId(anyInt());
		verify(diseasesService, times(1)).getAllTermsByDisease(anyString());
		assertThat(response).isEqualTo("In Danger");
	}

	// Early onset
	@Test
	void test_GivenMan40List9Notes_WhenGetAssessment_ThenReturnResponseEarlyOnset() {
		given(patientProxy.isExistByPatientId(anyInt())).willReturn(true);
		given(notePatientProxy.listNotesByPatientId(anyInt())).willReturn(list9Notes);
		given(patientProxy.getAgeAndGenreByPatientId(anyInt())).willReturn(man40);
		given(diseasesService.getAllTermsByDisease(anyString())).willReturn(termsToSearch);

		String response = assessmentRiskService.getAssessment(0, "");

		verify(patientProxy, times(1)).isExistByPatientId(anyInt());
		verify(notePatientProxy, times(1)).listNotesByPatientId(anyInt());
		verify(patientProxy, times(1)).getAgeAndGenreByPatientId(anyInt());
		verify(diseasesService, times(1)).getAllTermsByDisease(anyString());
		assertThat(response).isEqualTo("Early onset");
	}

	// TESTS AGE < 30
	// None
	@Test
	void test_GivenMan15List1Note_WhenGetAssessment_ThenReturnResponseNone() {
		given(patientProxy.isExistByPatientId(anyInt())).willReturn(true);
		given(notePatientProxy.listNotesByPatientId(anyInt())).willReturn(list1Note);
		given(patientProxy.getAgeAndGenreByPatientId(anyInt())).willReturn(man15);
		given(diseasesService.getAllTermsByDisease(anyString())).willReturn(termsToSearch);

		String response = assessmentRiskService.getAssessment(0, "");

		verify(patientProxy, times(1)).isExistByPatientId(anyInt());
		verify(notePatientProxy, times(1)).listNotesByPatientId(anyInt());
		verify(patientProxy, times(1)).getAgeAndGenreByPatientId(anyInt());
		verify(diseasesService, times(1)).getAllTermsByDisease(anyString());
		assertThat(response).isEqualTo("None");
	}

	// None
	@Test
	void test_GivenWoman27List3Notes_WhenGetAssessment_ThenReturnResponseNone() {
		given(patientProxy.isExistByPatientId(anyInt())).willReturn(true);
		given(notePatientProxy.listNotesByPatientId(anyInt())).willReturn(list3Notes);
		given(patientProxy.getAgeAndGenreByPatientId(anyInt())).willReturn(woman27);
		given(diseasesService.getAllTermsByDisease(anyString())).willReturn(termsToSearch);

		String response = assessmentRiskService.getAssessment(0, "");

		verify(patientProxy, times(1)).isExistByPatientId(anyInt());
		verify(notePatientProxy, times(1)).listNotesByPatientId(anyInt());
		verify(patientProxy, times(1)).getAgeAndGenreByPatientId(anyInt());
		verify(diseasesService, times(1)).getAllTermsByDisease(anyString());
		assertThat(response).isEqualTo("None");
	}

	// In Danger
	@Test
	void test_GivenMan15List3Notes_WhenGetAssessment_ThenReturnResponseInDanger() {
		given(patientProxy.isExistByPatientId(anyInt())).willReturn(true);
		given(notePatientProxy.listNotesByPatientId(anyInt())).willReturn(list3Notes);
		given(patientProxy.getAgeAndGenreByPatientId(anyInt())).willReturn(man15);
		given(diseasesService.getAllTermsByDisease(anyString())).willReturn(termsToSearch);

		String response = assessmentRiskService.getAssessment(0, "");

		verify(patientProxy, times(1)).isExistByPatientId(anyInt());
		verify(notePatientProxy, times(1)).listNotesByPatientId(anyInt());
		verify(patientProxy, times(1)).getAgeAndGenreByPatientId(anyInt());
		verify(diseasesService, times(1)).getAllTermsByDisease(anyString());
		assertThat(response).isEqualTo("In Danger");
	}

	// In Danger
	@Test
	void test_GivenWonan27List6Notes_WhenGetAssessment_ThenReturnResponseInDanger() {
		given(patientProxy.isExistByPatientId(anyInt())).willReturn(true);
		given(notePatientProxy.listNotesByPatientId(anyInt())).willReturn(list6Notes);
		given(patientProxy.getAgeAndGenreByPatientId(anyInt())).willReturn(woman27);
		given(diseasesService.getAllTermsByDisease(anyString())).willReturn(termsToSearch);

		String response = assessmentRiskService.getAssessment(0, "");

		verify(patientProxy, times(1)).isExistByPatientId(anyInt());
		verify(notePatientProxy, times(1)).listNotesByPatientId(anyInt());
		verify(patientProxy, times(1)).getAgeAndGenreByPatientId(anyInt());
		verify(diseasesService, times(1)).getAllTermsByDisease(anyString());
		assertThat(response).isEqualTo("In Danger");
	}

	// Early onset
	@Test
	void test_GivenMan15List6Notes_WhenGetAssessment_ThenReturnResponseEarlyOnset() {
		given(patientProxy.isExistByPatientId(anyInt())).willReturn(true);
		given(notePatientProxy.listNotesByPatientId(anyInt())).willReturn(list6Notes);
		given(patientProxy.getAgeAndGenreByPatientId(anyInt())).willReturn(man15);
		given(diseasesService.getAllTermsByDisease(anyString())).willReturn(termsToSearch);

		String response = assessmentRiskService.getAssessment(0, "");

		verify(patientProxy, times(1)).isExistByPatientId(anyInt());
		verify(notePatientProxy, times(1)).listNotesByPatientId(anyInt());
		verify(patientProxy, times(1)).getAgeAndGenreByPatientId(anyInt());
		verify(diseasesService, times(1)).getAllTermsByDisease(anyString());
		assertThat(response).isEqualTo("Early onset");
	}

	// Early onset
	@Test
	void test_GivenWoman27List9Notes_WhenGetAssessment_ThenReturnResponseEarlyOnset() {
		given(patientProxy.isExistByPatientId(anyInt())).willReturn(true);
		given(notePatientProxy.listNotesByPatientId(anyInt())).willReturn(list9Notes);
		given(patientProxy.getAgeAndGenreByPatientId(anyInt())).willReturn(woman27);
		given(diseasesService.getAllTermsByDisease(anyString())).willReturn(termsToSearch);

		String response = assessmentRiskService.getAssessment(0, "");

		verify(patientProxy, times(1)).isExistByPatientId(anyInt());
		verify(notePatientProxy, times(1)).listNotesByPatientId(anyInt());
		verify(patientProxy, times(1)).getAgeAndGenreByPatientId(anyInt());
		verify(diseasesService, times(1)).getAllTermsByDisease(anyString());
		assertThat(response).isEqualTo("Early onset");
	}

}
