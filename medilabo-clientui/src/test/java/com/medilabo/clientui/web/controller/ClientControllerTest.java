package com.medilabo.clientui.web.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import com.medilabo.clientui.beans.NoteBean;
import com.medilabo.clientui.beans.NotePatientBean;
import com.medilabo.clientui.beans.PatientBean;
import com.medilabo.clientui.dto.NoteDTO;
import com.medilabo.clientui.dto.PatientDTO;
import com.medilabo.clientui.enums.Genre;
import com.medilabo.clientui.services.DiseaseAssessmentService;
import com.medilabo.clientui.services.NotePatientService;
import com.medilabo.clientui.services.PatientService;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@TestPropertySource(properties = "spring.config.location=classpath:/application.properties")
class ClientControllerTest {

	@Autowired
	private ClientController controller;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	public PatientService patientService;
	
	@MockBean
	public DiseaseAssessmentService diseaseAssessmentService;
	
	@MockBean
	public NotePatientService notePatientService;
	
	private static PatientBean patient1;
	private static PatientBean patientBadFirstName;
	private static PatientBean patientBadBirthday;
	
	private static NoteBean noteBean1;
	private static List<NoteBean> listNotes = new ArrayList<>();

	private static NoteDTO noteDTO1;
	private static NoteDTO noteDTOBad;

	private static List<PatientDTO> listPatientDTO = new ArrayList<>();

	private static NotePatientBean notePatient1;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		patient1 = new PatientBean(
				0,
				"firstName1",
				"lastName1",
				LocalDate.now(),
				Genre.MAN,
				"",
				"");

		patientBadFirstName = new PatientBean(
				0,
				"",
				"",
				LocalDate.now(),
				Genre.MAN,
				"",
				"");

		patientBadBirthday = new PatientBean(
				0,
				"firstName1",
				"lastName1",
				LocalDate.now().plusDays(1),
				Genre.MAN,
				"",
				"");

		noteBean1 = new NoteBean(UUID.randomUUID(), LocalDate.now(), "note1");
		listNotes.add(noteBean1);
		listNotes.add(new NoteBean(UUID.randomUUID(), LocalDate.now(), "note2"));

		noteDTO1 = new NoteDTO(0, noteBean1);
		noteDTOBad = new NoteDTO(0, new NoteBean(null, null, ""));
		
		listPatientDTO.add(new PatientDTO(0, "firstName1", "lastName1"));
		listPatientDTO.add(new PatientDTO(1, "firstName2", "lastName2"));
		
		notePatient1 = new NotePatientBean("0", 0, listNotes);
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
	void test_WhenGetPatientInfosByPatientId_ThenReturnToHtmlPagePatientList() throws Exception {
		given(patientService.getPatientById(anyInt())).willReturn(Optional.of(patient1));
		given(diseaseAssessmentService.getAssessment(anyInt(), anyString())).willReturn("None");
		given(notePatientService.listNotesByPatientId(anyInt())).willReturn(listNotes);
		
		mockMvc
			.perform(get("/patient/{patientId}", 0))
			.andExpect(model().attributeExists("disease"))
			.andExpect(model().attributeExists("assessment"))
			.andExpect(model().attributeExists("patient"))
			.andExpect(model().attributeExists("listNotesDTO"))
			.andExpect(model().attributeExists("noteDTO"))
			.andExpect(view().name("patient/infos"))
			.andExpect(status().isOk());
		
		verify(patientService, times(1)).getPatientById(anyInt());
		verify(diseaseAssessmentService, times(1)).getAssessment(anyInt(), anyString());
		verify(notePatientService, times(1)).listNotesByPatientId(anyInt());
	}

	@Test
	void test_GivenBadPatientId_WhenGetPatientInfosByPatientId_ThenReturnToHtmlPagePatientList() throws Exception {
		given(patientService.getPatientById(anyInt())).willReturn(Optional.empty());
		
		mockMvc
			.perform(get("/patient/{patientId}", 0))
			.andExpect(view().name("patient/list"))
			.andExpect(status().isOk());
		
		verify(patientService, times(1)).getPatientById(anyInt());
		verify(diseaseAssessmentService, times(0)).getAssessment(anyInt(), anyString());
		verify(notePatientService, times(0)).listNotesByPatientId(anyInt());
	}

	@Test
	void test_WhenAddPatientForm_ThenReturnToHtmlPagePatientAdd() throws Exception {
		mockMvc
			.perform(get("/patient/add"))
			.andExpect(model().attributeExists("patient"))
			.andExpect(view().name("patient/add"))
			.andExpect(status().isOk());
	}

	@Test
	void test_WhenSavePatient_ThenRedirectToHtmlPagePatientList() throws Exception {
		given(patientService.isBirthdayValid(any())).willReturn(true);
		given(patientService.savePatient(any(PatientBean.class))).willReturn(0);
		given(notePatientService.saveNotePatient(any(NotePatientBean.class))).willReturn(new ResponseEntity<NotePatientBean>(notePatient1, HttpStatus.CREATED));

		mockMvc
			.perform(post("/patient/save")
					.flashAttr("patient", patient1))
			.andExpect(view().name("redirect:/patient/list"))
			.andExpect(status().is3xxRedirection());
		
		verify(patientService, times(1)).isBirthdayValid(any());
		verify(patientService, times(1)).savePatient(any(PatientBean.class));
		verify(notePatientService, times(1)).saveNotePatient(any(NotePatientBean.class));
	}

	@Test
	void test_GivenNoValidPatient_WhenSavePatient_ThenRedirectToHtmlPagePatientAdd() throws Exception {
		mockMvc
			.perform(post("/patient/save")
				.flashAttr("patient", new PatientBean()))
			.andExpect(view().name("redirect:/patient/add"))
			.andExpect(status().is3xxRedirection());
		
		verify(patientService, times(0)).isBirthdayValid(any());
		verify(patientService, times(0)).savePatient(any(PatientBean.class));
		verify(notePatientService, times(0)).saveNotePatient(any(NotePatientBean.class));
	}

	@Test
	void test_GivenBadBirthday_WhenSavePatient_ThenRedirectToHtmlPagePatientAdd() throws Exception {
		given(patientService.isBirthdayValid(any())).willReturn(false);

		mockMvc
			.perform(post("/patient/save")
				.flashAttr("patient", patientBadBirthday))
			.andExpect(view().name("redirect:/patient/add"))
			.andExpect(status().is3xxRedirection());
		
		verify(patientService, times(1)).isBirthdayValid(any());
		verify(patientService, times(0)).savePatient(any(PatientBean.class));
		verify(notePatientService, times(0)).saveNotePatient(any(NotePatientBean.class));
	}

	@Test
	void test_GivenErrorSaveNotePatient_WhenSavePatient_ThenRedirectToHtmlPagePatientAdd() throws Exception {
		given(patientService.isBirthdayValid(any())).willReturn(true);
		given(patientService.savePatient(any())).willReturn(-1);
		given(notePatientService.saveNotePatient(any(NotePatientBean.class))).willReturn(new ResponseEntity<NotePatientBean>(notePatient1, HttpStatus.INTERNAL_SERVER_ERROR));

		mockMvc
			.perform(post("/patient/save")
				.flashAttr("patient", patient1))
			.andExpect(view().name("redirect:/patient/list"))
			.andExpect(status().is3xxRedirection());
		
		verify(patientService, times(1)).isBirthdayValid(any());
		verify(patientService, times(1)).savePatient(any(PatientBean.class));
		verify(notePatientService, times(0)).saveNotePatient(any(NotePatientBean.class));
	}

	@Test
	void test_GivenGoodPatientBean_WhenUpdatePatient_ThenRedirectToHtmlPagePatientUpdate() throws Exception {
		given(patientService.isBirthdayValid(any())).willReturn(true);

		mockMvc
			.perform(post("/patient/update")
					.flashAttr("patient", patient1))
			.andExpect(view().name("redirect:/patient/" + patient1.getId()))
			.andExpect(status().is3xxRedirection());
		
		verify(patientService, times(1)).isBirthdayValid(any());
		verify(patientService, times(1)).updatePatient(any(PatientBean.class));
	}

	@Test
	void test_GivenBadFirstName_WhenUpdatePatient_ThenRedirectToHtmlPagePatientUpdate() throws Exception {
		mockMvc
			.perform(post("/patient/update")
					.flashAttr("patient", patientBadFirstName))
			.andExpect(view().name("redirect:/patient/" + patientBadFirstName.getId()))
			.andExpect(status().is3xxRedirection());
		
		verify(patientService, times(0)).isBirthdayValid(any());
		verify(patientService, times(0)).updatePatient(any(PatientBean.class));
	}

	@Test
	void test_GivenBadBirthday_WhenUpdatePatient_ThenRedirectToHtmlPagePatientUpdate() throws Exception {
		given(patientService.isBirthdayValid(any())).willReturn(false);

		mockMvc
			.perform(post("/patient/update")
					.flashAttr("patient", patientBadBirthday))
			.andExpect(view().name("redirect:/patient/" + patientBadBirthday.getId()))
			.andExpect(status().is3xxRedirection());
		
		verify(patientService, times(1)).isBirthdayValid(any());
		verify(patientService, times(0)).updatePatient(any(PatientBean.class));
	}

	@Test
	void test_WhenDeletePatientByPatientId_ThenRedirectToHtmlPagePatientList() throws Exception {
		mockMvc
			.perform(get("/patient/{patientId}/delete", 0))
			.andExpect(view().name("redirect:/patient/list"))
			.andExpect(status().is3xxRedirection());
		
		verify(patientService, times(1)).deletePatientByPatientId(anyInt());
		verify(notePatientService, times(1)).deleteNotePatientByPatientId(anyInt());
	}

	@WithMockUser
	@Test
	void test_WhenListPatients_ThenReturnToHtmlPagePatientList() throws Exception {
		mockMvc
			.perform(get("/patient/list"))
			.andExpect(model().attributeExists("patients"))
			.andExpect(model().attributeExists("username"))
			.andExpect(view().name("patient/list"))
			.andExpect(status().isOk());
		
		verify(patientService, times(1)).listPatients();
	}

	@Test
	void test_WhenSaveNote_ThenRedirectToHtmlPagePatientId() throws Exception {
		mockMvc
			.perform(post("/patient/note/save")
					.flashAttr("noteDTO", noteDTO1))
			.andExpect(view().name("redirect:/patient/" + noteDTO1.getPatientId()))
			.andExpect(status().is3xxRedirection());
		
		verify(notePatientService, times(1)).saveNoteByPatientId(any(NoteDTO.class));
	}

	@Test
	void test_GivenNoteDTOBad_WhenSaveNotePatient_ThenRedirectToHtmlPagePatientId() throws Exception {
		mockMvc
			.perform(post("/patient/note/save")
					.flashAttr("noteDTO", noteDTOBad))
			.andExpect(view().name("redirect:/patient/" + noteDTOBad.getPatientId()))
			.andExpect(status().is3xxRedirection());
		
		verify(notePatientService, times(1)).saveNoteByPatientId(any(NoteDTO.class));
	}

	@Test
	void test_WhenUpdateNoteByPatientId_ThenReturnToHtmlPagePatientId() throws Exception {
		given(notePatientService.getNoteById(any(UUID.class))).willReturn(Optional.of(noteBean1));
		given(patientService.getPatientById(anyInt())).willReturn(Optional.of(patient1));
		given(diseaseAssessmentService.getAssessment(anyInt(), anyString())).willReturn("None");
		given(notePatientService.listNotesByPatientId(anyInt())).willReturn(listNotes);
		
		mockMvc
			.perform(get("/patient/{patientId}/note/{noteId}/update", 0, UUID.randomUUID()))
			.andExpect(model().attributeExists("disease"))
			.andExpect(model().attributeExists("assessment"))
			.andExpect(model().attributeExists("patient"))
			.andExpect(model().attributeExists("listNotesDTO"))
			.andExpect(model().attributeExists("noteDTO"))
			.andExpect(view().name("patient/infos"))
			.andExpect(status().isOk());
		
		verify(notePatientService, times(1)).getNoteById(any(UUID.class));
		verify(patientService, times(1)).getPatientById(anyInt());
		verify(diseaseAssessmentService, times(1)).getAssessment(anyInt(), anyString());
		verify(notePatientService, times(1)).listNotesByPatientId(anyInt());
	}

	@Test
	void test_GivenBadNoteId_WhenUpdateNoteByPatientId_ThenRedirectToHtmlPagePatientList() throws Exception {
		given(notePatientService.getNoteById(any(UUID.class))).willReturn(Optional.empty());
		
		mockMvc
			.perform(get("/patient/{patientId}/note/{noteId}/update", 0, UUID.randomUUID()))
			.andExpect(view().name("redirect:/patient/list"))
			.andExpect(status().is3xxRedirection());
		
		verify(notePatientService, times(1)).getNoteById(any(UUID.class));
		verify(patientService, times(0)).getPatientById(anyInt());
		verify(diseaseAssessmentService, times(0)).getAssessment(anyInt(), anyString());
		verify(notePatientService, times(0)).listNotesByPatientId(anyInt());
	}

	@Test
	void test_GivenBadPatientIdWhenUpdateNoteByPatientId_ThenReturnToHtmlPagePatientList() throws Exception {
		given(notePatientService.getNoteById(any(UUID.class))).willReturn(Optional.of(noteBean1));
		given(patientService.getPatientById(anyInt())).willReturn(Optional.empty());
		
		mockMvc
			.perform(get("/patient/{patientId}/note/{noteId}/update", 0, UUID.randomUUID()))
			.andExpect(view().name("patient/list"))
			.andExpect(status().isOk());
		
		verify(notePatientService, times(1)).getNoteById(any(UUID.class));
		verify(patientService, times(1)).getPatientById(anyInt());
		verify(diseaseAssessmentService, times(0)).getAssessment(anyInt(), anyString());
		verify(notePatientService, times(0)).listNotesByPatientId(anyInt());
	}

	@Test
	void test_WhenDeleteNoteById_ThenRedirectToHtmlPagePatientId() throws Exception {
		mockMvc
			.perform(get("/patient/{patientId}/note/{noteId}/delete", 0, noteBean1.getId()))
			.andExpect(view().name("redirect:/patient/0"))
			.andExpect(status().is3xxRedirection());
		
		verify(notePatientService, times(1)).deleteNoteById(any(UUID.class));
	}

}
