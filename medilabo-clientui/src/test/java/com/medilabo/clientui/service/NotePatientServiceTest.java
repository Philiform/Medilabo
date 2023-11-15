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
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.medilabo.clientui.beans.NoteBean;
import com.medilabo.clientui.beans.NotePatientBean;
import com.medilabo.clientui.beans.UuidClassBean;
import com.medilabo.clientui.dto.NoteDTO;
import com.medilabo.clientui.proxies.MicroserviceNotePatientProxy;
import com.medilabo.clientui.services.NotePatientServiceImpl;

@ExtendWith(MockitoExtension.class)
class NotePatientServiceTest {

	@InjectMocks
	private NotePatientServiceImpl service;

	@Mock
	private MicroserviceNotePatientProxy proxy;
	
	private NoteBean note1;
	private NoteBean note2;
	private NoteBean noteForSave;
	private NotePatientBean notePatient1;
	private NoteDTO noteDTOForUpdate;
	private NoteDTO noteDTOForSave;
	
	private List<NoteBean> listNotes = new ArrayList<>();
	
	@BeforeEach
	void setUp() throws Exception {
		note1 = new NoteBean(UUID.randomUUID(), LocalDate.now(), "note1");
		note2 = new NoteBean(UUID.randomUUID(), LocalDate.now(), "note2");

		listNotes.add(note1);
		listNotes.add(note2);
		
		notePatient1 = new NotePatientBean("0", 0, listNotes);
		
		noteForSave = new NoteBean(null, null, "noteForSave");
		
		noteDTOForUpdate = new NoteDTO(0, note1);
		noteDTOForSave = new NoteDTO(0, noteForSave);
	}
	
	@Test
	void test_WhenListNotesByPatientId_ThenReturn2Notes() {
		given(proxy.listNotesByPatientId(anyInt())).willReturn(listNotes);

		List<NoteBean> response = service.listNotesByPatientId(0);

		verify(proxy, times(1)).listNotesByPatientId(anyInt());
		assertThat(response.size()).isEqualTo(listNotes.size());
	}

	@Test
	void test_WhenGetNotePatientByPatientId_ThenReturn1Patient() {
		given(proxy.getNotePatientByPatientId(anyInt())).willReturn(Optional.of(notePatient1));

		Optional<NotePatientBean> response = service.getNotePatientByPatientId(0);

		verify(proxy, times(1)).getNotePatientByPatientId(anyInt());
		assertThat(response.get()).isEqualTo(notePatient1);
	}

	@Test
	void test_WhenSaveNotePatient_ThenReturnHttpStatusCreated() {
		given(proxy.saveNotePatient(any(NotePatientBean.class))).willReturn(new ResponseEntity<NotePatientBean>(notePatient1, HttpStatus.CREATED));

		ResponseEntity<NotePatientBean> response = service.saveNotePatient(notePatient1);

		verify(proxy, times(1)).saveNotePatient(any(NotePatientBean.class));
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
	}

	@Test
	void test_WhenUpdateNotePatient_ThenSaveNotePatient() {
		service.updateNotePatient(notePatient1);

		verify(proxy, times(1)).updateNotePatient(any(NotePatientBean.class));
	}

	@Test
	void test_WhenDeleteNotePatientByPatientId_ThenDeleteNotePatient() {
		service.deleteNotePatientByPatientId(0);

		verify(proxy, times(1)).deleteNotePatientByPatientId(anyInt());
	}

	@Test
	void test_WhenGetNoteById_ThenReturnOptionalNoteBean() {
		given(proxy.getNoteById(any(UuidClassBean.class))).willReturn(Optional.of(note1));

		Optional<NoteBean> response = service.getNoteById(UUID.randomUUID());

		verify(proxy, times(1)).getNoteById(any(UuidClassBean.class));
		assertThat(response.get()).isEqualTo(note1);
	}

	@Test
	void test_WhenSaveNoteByPatientId_ThenUpdateNote() {
		service.saveNoteByPatientId(noteDTOForUpdate);

		verify(proxy, times(1)).updateNote(any(NoteBean.class));
	}

	@Test
	void test_WhenSaveNoteByPatientId_ThenSaveNote() {
		service.saveNoteByPatientId(noteDTOForSave);

		verify(proxy, times(1)).saveNoteByPatientId(anyInt(), any(NoteBean.class));
	}

	@Test
	void test_WhenDeleteNoteById_ThenDeleteNote() {
		service.deleteNoteById(UUID.randomUUID());

		verify(proxy, times(1)).deleteNoteById(any(UuidClassBean.class));
	}

}
