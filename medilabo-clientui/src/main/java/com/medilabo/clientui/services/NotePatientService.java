package com.medilabo.clientui.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.ResponseEntity;

import com.medilabo.clientui.beans.NoteBean;
import com.medilabo.clientui.beans.NotePatientBean;
import com.medilabo.clientui.dto.NoteDTO;

// TODO: Auto-generated Javadoc
/**
 * The Interface NotePatientService.
 */
public interface NotePatientService {

	/**
	 * Gets the note patient by patient id.
	 *
	 * @param patientId the patient id
	 * @return the note patient by patient id
	 */
	//======= NotePatient =======
	Optional<NotePatientBean> getNotePatientByPatientId(final Integer patientId);
	
	/**
	 * Save note patient.
	 *
	 * @param notePatientBean the note patient bean
	 * @return the response entity
	 */
	ResponseEntity<NotePatientBean> saveNotePatient(final NotePatientBean notePatientBean);
	
	/**
	 * Update note patient.
	 *
	 * @param notePatientBean the note patient bean
	 */
	void updateNotePatient(final NotePatientBean notePatientBean);
	
	/**
	 * Delete note patient by patient id.
	 *
	 * @param patientId the patient id
	 */
	void deleteNotePatientByPatientId(final Integer patientId);

	/**
	 * List notes by patient id.
	 *
	 * @param patientId the patient id
	 * @return the list
	 */
	//======= Note =======
	List<NoteBean> listNotesByPatientId(final Integer patientId);
	
	/**
	 * Gets the note by id.
	 *
	 * @param noteId the note id
	 * @return the note by id
	 */
	Optional<NoteBean> getNoteById(final UUID noteId);
	
	/**
	 * Save note by patient id.
	 *
	 * @param noteDTO the note DTO
	 */
	void saveNoteByPatientId(final NoteDTO noteDTO);
	
	/**
	 * Delete note by id.
	 *
	 * @param noteId the note id
	 */
	void deleteNoteById(final UUID noteId);
}
