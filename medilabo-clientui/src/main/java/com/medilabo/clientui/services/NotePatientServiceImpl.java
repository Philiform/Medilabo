package com.medilabo.clientui.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.medilabo.clientui.beans.NoteBean;
import com.medilabo.clientui.beans.NotePatientBean;
import com.medilabo.clientui.beans.UuidClassBean;
import com.medilabo.clientui.dto.NoteDTO;
import com.medilabo.clientui.proxies.MicroserviceNotePatientProxy;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// TODO: Auto-generated Javadoc
/**
 * The Class NotePatientServiceImpl.
 */
@Service

/**
 * Instantiates a new note patient service impl.
 *
 * @param proxy the proxy
 */
@RequiredArgsConstructor

/** The Constant log. */
@Slf4j
public class NotePatientServiceImpl implements NotePatientService {

	/** The proxy. */
	private final MicroserviceNotePatientProxy proxy;
	
	/**
	 * Gets the note patient by patient id.
	 *
	 * @param patientId the patient id
	 * @return the note patient by patient id
	 */
	@Override
	public Optional<NotePatientBean> getNotePatientByPatientId(final Integer patientId) {
		return proxy.getNotePatientByPatientId(patientId);
	}

	/**
	 * Save note patient.
	 *
	 * @param notePatient the note patient
	 * @return the response entity
	 */
	@Override
	public ResponseEntity<NotePatientBean> saveNotePatient(final NotePatientBean notePatient) {
		return proxy.saveNotePatient(notePatient);
	}

	/**
	 * Update note patient.
	 *
	 * @param notePatient the note patient
	 */
	@Override
	public void updateNotePatient(final NotePatientBean notePatient) {
		proxy.updateNotePatient(notePatient);
	}

	/**
	 * Delete note patient by patient id.
	 *
	 * @param patientId the patient id
	 */
	@Override
	public void deleteNotePatientByPatientId(final Integer patientId) {
		proxy.deleteNotePatientByPatientId(patientId);
	}

	/**
	 * List notes by patient id.
	 *
	 * @param patientId the patient id
	 * @return the list
	 */
	@Override
	public List<NoteBean> listNotesByPatientId(final Integer patientId) {
		return proxy.listNotesByPatientId(patientId);
	}

	/**
	 * Gets the note by id.
	 *
	 * @param noteId the note id
	 * @return the note by id
	 */
	@Override
	public Optional<NoteBean> getNoteById(final UUID noteId) {
		return proxy.getNoteById(new UuidClassBean(noteId));
	}

	/**
	 * Save note by patient id.
	 *
	 * @param noteDTO the note DTO
	 */
	@Override
	public void saveNoteByPatientId(final NoteDTO noteDTO) {
		log.debug("saveNoteByPatientId");
		
		if(noteDTO.getNoteBean().getDate() == null) {
			noteDTO.getNoteBean().setDate(LocalDate.now());
		}
		log.debug("NoteBean = " + noteDTO.getNoteBean());
		log.debug("patientId = " + noteDTO.getPatientId());

		if(noteDTO.getNoteBean().getId() == null) {
			proxy.saveNoteByPatientId(noteDTO.getPatientId(), noteDTO.getNoteBean());
		} else {
			proxy.updateNote(noteDTO.getNoteBean());
		}
	}

	/**
	 * Delete note by id.
	 *
	 * @param noteId the note id
	 */
	@Override
	public void deleteNoteById(final UUID noteId) {
		proxy.deleteNoteById(new UuidClassBean(noteId));
	}

}
