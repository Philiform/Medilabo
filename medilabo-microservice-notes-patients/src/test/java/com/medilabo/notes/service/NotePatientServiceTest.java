package com.medilabo.notes.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.medilabo.notes.dao.NotePatientDao;
import com.medilabo.notes.exception.NotePatientNotFoundException;
import com.medilabo.notes.exception.UnableAddNotePatientException;
import com.medilabo.notes.model.Note;
import com.medilabo.notes.model.NotePatient;

@ExtendWith(MockitoExtension.class)
class NotePatientServiceTest {

	@InjectMocks
	private NotePatientServiceImpl service;

	@Mock
	private NotePatientDao dao;
	
	private Note note1;
	private Note note2;
	private NotePatient notePatient1;
	
	private List<Note> listNotes = new ArrayList<>();
	
	@BeforeEach
	void setUp() throws Exception {
		note1 = new Note(UUID.randomUUID(), LocalDate.now(), "note1");
		note2 = new Note(UUID.randomUUID(), LocalDate.now(), "note2");

		listNotes.add(note1);
		listNotes.add(note2);
		
		notePatient1 = new NotePatient("0", 0, listNotes);
	}
	
	@Test
	void test_WhenListNotesByPatientId_ThenReturn2Notes() {
		given(dao.findByPatientId(anyInt())).willReturn(Optional.of(notePatient1));

		List<Note> response = service.listNotesByPatientId(0);

		verify(dao, times(1)).findByPatientId(anyInt());
		assertThat(response.size()).isEqualTo(listNotes.size());
	}

	@Test
	void test_WhenListNotesByPatientId_ThenReturn0Note() {
		given(dao.findByPatientId(anyInt())).willReturn(Optional.empty());

		List<Note> response = service.listNotesByPatientId(0);

		verify(dao, times(1)).findByPatientId(anyInt());
		assertThat(response.size()).isEqualTo(0);
	}

	@Test
	void test_WhenGetNotePatientByPatientId_ThenReturn1Patient() {
		given(dao.findByPatientId(anyInt())).willReturn(Optional.of(notePatient1));

		Optional<NotePatient> response = service.getNotePatientByPatientId(0);

		verify(dao, times(1)).findByPatientId(anyInt());
		assertThat(response.get()).isEqualTo(notePatient1);
	}

	@Test
	void test_WhenGetPatientById_ThenThrowNotePatientNotFoundException() {
		assertThrows(NotePatientNotFoundException.class, () -> {
			given(dao.findByPatientId(anyInt())).willReturn(Optional.empty());

			service.getNotePatientByPatientId(0);
		});
		verify(dao, times(1)).findByPatientId(anyInt());
	}

	@Test
	void test_WhenSaveNotePatient_ThenReturnNotePatientSaved() {
		given(dao.save(any(NotePatient.class))).willReturn(notePatient1);

		NotePatient response = service.saveNotePatient(notePatient1);

		verify(dao, times(1)).save(any(NotePatient.class));
		assertThat(response).isEqualTo(notePatient1);
	}

	@Test
	void test_WhenSaveNotePatient_ThenThrowUnableAddNotePatientException() {
		assertThrows(UnableAddNotePatientException.class, () -> {
			given(dao.save(any(NotePatient.class))).willReturn(null);

			service.saveNotePatient(notePatient1);
		});
		verify(dao, times(1)).save(any(NotePatient.class));
	}

	@Test
	void test_WhenUpdateNotePatient_ThenSaveNotePatient() {
		given(dao.save(any(NotePatient.class))).willReturn(notePatient1);

		service.updateNotePatient(notePatient1);

		verify(dao, times(1)).save(any(NotePatient.class));
	}

	@Test
	void test_WhenDeleteNotePatientByPatientId_ThenDeleteNotePatient() {
		given(dao.findByPatientId(anyInt())).willReturn(Optional.of(notePatient1));

		service.deleteNotePatientByPatientId(notePatient1.getPatientId());

		verify(dao, times(1)).findByPatientId(anyInt());
		verify(dao, times(1)).delete(any(NotePatient.class));
	}

	@Test
	void test_WhenGetNoteById_ThenReturn1Note() {
		given(dao.findNotePatientByNoteId(anyString())).willReturn(Optional.of(notePatient1));

		Optional<Note> response = service.getNoteById(note1.getId());

		verify(dao, times(1)).findNotePatientByNoteId(anyString());
		assertThat(response.get()).isEqualTo(note1);
	}

	@Test
	void test_WhenGetNoteById_ThenThrowNoteNotFoundException() {
		assertThrows(NotePatientNotFoundException.class, () -> {
			given(dao.findNotePatientByNoteId(anyString())).willReturn(Optional.empty());

			service.getNoteById(note1.getId());
		});

		verify(dao, times(1)).findNotePatientByNoteId(anyString());
	}

	@Test
	void test_GivenGoodNotePatientAndGoodNote_WhenSaveNote_ThenSaveNote() {
		Note newNote = new Note();
		newNote.setId(null);
		newNote.setDate(LocalDate.now());
		newNote.setNote("newNote");
		
		given(dao.findByPatientId(anyInt())).willReturn(Optional.of(notePatient1));
		given(dao.findPatientIdByNoteId(anyString())).willReturn(new ArrayList<>());
		given(dao.save(any(NotePatient.class))).willReturn(notePatient1);

		NotePatient response = service.saveNoteByPatientId(0, newNote);

		verify(dao, times(1)).findByPatientId(anyInt());
		verify(dao, times(1)).findPatientIdByNoteId(anyString());
		verify(dao, times(1)).save(any(NotePatient.class));
		assertThat(response).isEqualTo(notePatient1);
	}

	@Test
	void test_GivenBadNotePatientAndGoodNote_WhenSaveNote_ThenThrowUnableAddNoteException() {
		assertThrows(NotePatientNotFoundException.class, () -> {
			given(dao.findByPatientId(anyInt())).willReturn(Optional.empty());

			service.saveNoteByPatientId(0, note1);
		});
		verify(dao, times(1)).findByPatientId(anyInt());
		verify(dao, times(0)).save(any(NotePatient.class));
	}

	@Test
	void test_WhenUpdateNote_ThenSaveNote() {
		given(dao.findNotePatientByNoteId(anyString())).willReturn(Optional.of(notePatient1));

		service.updateNote(note1);

		verify(dao, times(1)).findNotePatientByNoteId(anyString());
		verify(dao, times(1)).save(any(NotePatient.class));
	}

	@Test
	void test_WhenDeleteNoteById_ThenDeleteNote() {
		given(dao.findNotePatientByNoteId(anyString())).willReturn(Optional.of(notePatient1));

		service.deleteNoteById(note1.getId());

		verify(dao, times(1)).findNotePatientByNoteId(anyString());
		verify(dao, times(1)).save(any(NotePatient.class));
	}

}
