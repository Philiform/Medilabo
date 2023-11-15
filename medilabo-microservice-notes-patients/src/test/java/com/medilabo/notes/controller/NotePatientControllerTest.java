package com.medilabo.notes.controller;

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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.medilabo.notes.exception.NotePatientNotFoundException;
import com.medilabo.notes.exception.UnableAddNotePatientException;
import com.medilabo.notes.model.Note;
import com.medilabo.notes.model.NotePatient;
import com.medilabo.notes.model.UuidClass;
import com.medilabo.notes.service.NotePatientService;

@WebMvcTest(controllers = NotePatientController.class)
@TestPropertySource(properties = "spring.config.location=classpath:/application.properties")
class NotePatientControllerTest {

	@Autowired
	private NotePatientController controller;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	public NotePatientService service;
	
	static private Note note1;
	static private Note note2;
	static private Note noteBad;
	static private NotePatient notePatient1;
	static private UuidClass uuidClass;
	
	static private List<Note> listNotes = new ArrayList<>();

	@BeforeAll
	static void setUp() throws Exception {

		note1 = new Note(UUID.randomUUID(), LocalDate.now(), "note1");
		note2 = new Note(UUID.randomUUID(), LocalDate.now(), "note2");

		listNotes.add(note1);
		listNotes.add(note2);
		
		noteBad = new Note(UUID.randomUUID(), LocalDate.now(), "");

		notePatient1 = new NotePatient("0", 0, listNotes);
		
		uuidClass = new UuidClass(UUID.randomUUID());
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
	void test_WhenGetNotePatientById_ThenReturnHttpStatusOk() throws Exception {
		given(service.getNotePatientByPatientId(anyInt())).willReturn(Optional.of(notePatient1));

		mockMvc
			.perform(get("/patient/{patientId}/notePatient", 0))
			.andExpect(status().isOk());
		
		verify(service, times(1)).getNotePatientByPatientId(anyInt());
	}

	@Test
	void test_WhenGetNotePatientById_ThenReturnHttpStatusInternalServerError() throws Exception {
		given(service.getNotePatientByPatientId(anyInt())).willThrow(new NotePatientNotFoundException(""));

		mockMvc
			.perform(get("/patient/{patientId}/notePatient", 0))
			.andExpect(status().isInternalServerError());
		
		verify(service, times(1)).getNotePatientByPatientId(anyInt());
	}

	@Test
	void test_WhenSaveNotePatient_ThenReturnHttpStatusCreated() throws Exception {
		given(service.saveNotePatient(any(NotePatient.class))).willReturn(notePatient1);

		mockMvc
		.perform(post("/notePatient/save")
				.content(new ObjectMapper().writeValueAsString(notePatient1))
			    .contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated());
		
		verify(service, times(1)).saveNotePatient(any());
	}

	@Test
	void test_WhenSaveNotePatient_ThenReturnHttpStatusInternalServerError() throws Exception {
		given(service.saveNotePatient(any(NotePatient.class))).willThrow(new UnableAddNotePatientException(""));

		mockMvc
		.perform(post("/notePatient/save")
				.content(new ObjectMapper().writeValueAsString(notePatient1))
			    .contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isInternalServerError());
		
		verify(service, times(1)).saveNotePatient(any(NotePatient.class));
	}

	@Test
	void test_WhenUpdateNotePatient_ThenReturnHttpStatusOk() throws Exception {
		mockMvc
			.perform(put("/notePatient/update")
				.content(new ObjectMapper().writeValueAsString(notePatient1))
			    .contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
		
		verify(service, times(1)).updateNotePatient(any(NotePatient.class));
	}

	@Test
	void test_WhenDeleteNotePatient_ThenReturnHttpStatusOk() throws Exception {
		mockMvc
			.perform(delete("/patient/{patientId}/notePatient/delete", 0))
			.andExpect(status().isOk());
		
		verify(service, times(1)).deleteNotePatientByPatientId(anyInt());
	}

	@Test
	void test_WhenListNotePatients_ThenReturnHttpStatusOk() throws Exception {
		given(service.listNotesByPatientId(anyInt())).willReturn(listNotes);

		mockMvc
			.perform(get("/patient/{patientId}/notePatient/listNotes", 0))
			.andExpect(status().isOk());
		
		verify(service, times(1)).listNotesByPatientId(anyInt());
	}

	@Test
	void test_GivenGoodNote_WhenGetNoteById_ThenReturnHttpStatusOk() throws Exception {
		given(service.getNoteById(any(UUID.class))).willReturn(Optional.of(note1));

		mockMvc
			.perform(post("/notePatient/note")
				.content(new ObjectMapper().writeValueAsString(uuidClass))
			    .contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
		
		verify(service, times(1)).getNoteById(any(UUID.class));
	}

	@Test
	void test_GivenBadNoteId_WhenGetNoteById_ThenReturnHttpStatusOk() throws Exception {
		given(service.getNoteById(any(UUID.class))).willReturn(Optional.empty());

		mockMvc
			.perform(post("/notePatient/note")
				.content(new ObjectMapper().writeValueAsString(uuidClass))
			    .contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
		
		verify(service, times(1)).getNoteById(any(UUID.class));
	}

	@Test
	void test_GivenGoodPatientIdAndGoodNote_WhenSaveNoteByPatientId_ThenReturnHttpStatusCreated() throws Exception {
		given(service.saveNoteByPatientId(anyInt(), any(Note.class))).willReturn(notePatient1);

		mockMvc
			.perform(post("/patient/{patientId}/notePatient/note/save", 0)
				.content(new ObjectMapper().writeValueAsString(note1))
			    .contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated());
		
		verify(service, times(1)).saveNoteByPatientId(anyInt(), any(Note.class));
	}

	@Test
	void test_WhenGivenGoodNote_SaveNoteByPatientId_ThenReturnHttpStatusInternalServerError() throws Exception {
		given(service.saveNoteByPatientId(anyInt(), any(Note.class))).willThrow(new NotePatientNotFoundException(""));

		mockMvc
			.perform(post("/patient/{patientId}/notePatient/note/save", 0)
				.content(new ObjectMapper().writeValueAsString(note1))
			    .contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isInternalServerError());
		
		verify(service, times(1)).saveNoteByPatientId(anyInt(), any(Note.class));
	}

	@Test
	void test_GivenBadNote_WhenSaveNoteByPatientId_ThenReturnHttpStatusInternalServerError() throws Exception {
		mockMvc
			.perform(post("/patient/{patientId}/notePatient/note/save", 0)
				.content(new ObjectMapper().writeValueAsString(noteBad))
			    .contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isInternalServerError());
		
		verify(service, times(0)).saveNoteByPatientId(anyInt(), any(Note.class));
	}

	@Test
	void test_WhenUpdateNote_ThenReturnHttpStatusOk() throws Exception {
		mockMvc
			.perform(put("/notePatient/note/update")
				.content(new ObjectMapper().writeValueAsString(note1))
			    .contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
		
		verify(service, times(1)).updateNote(any(Note.class));
	}

	@Test
	void test_WhenDeleteNote_ThenReturnHttpStatusOk() throws Exception {
		mockMvc
			.perform(delete("/notePatient/note/delete")
					.content(new ObjectMapper().writeValueAsString(uuidClass))
				    .contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
		
		verify(service, times(1)).deleteNoteById(any(UUID.class));
	}

}
