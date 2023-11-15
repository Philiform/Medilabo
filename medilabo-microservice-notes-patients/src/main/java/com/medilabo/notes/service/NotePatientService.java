package com.medilabo.notes.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.medilabo.notes.model.Note;
import com.medilabo.notes.model.NotePatient;

// TODO: Auto-generated Javadoc
/**
 * The Interface NotePatientService.
 */
public interface NotePatientService {

	/**
	 * Gets the note patient by patient id.
	 *
	 * @param id the id
	 * @return the note patient by patient id
	 */
	//======= NotePatient =======
	Optional<NotePatient> getNotePatientByPatientId(final Integer id);
	
	/**
	 * Save note patient.
	 *
	 * @param notePatient the note patient
	 * @return the note patient
	 */
	NotePatient saveNotePatient(final NotePatient notePatient);
	
	/**
	 * Update note patient.
	 *
	 * @param notePatient the note patient
	 */
	void updateNotePatient(final NotePatient notePatient);
	
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
	List<Note> listNotesByPatientId(final Integer patientId);
	
	/**
	 * Gets the note by id.
	 *
	 * @param noteId the note id
	 * @return the note by id
	 */
	Optional<Note> getNoteById(final UUID noteId);
	
	/**
	 * Save note by patient id.
	 *
	 * @param patientId the patient id
	 * @param note the note
	 * @return the note patient
	 */
	NotePatient saveNoteByPatientId(final Integer patientId, final Note note);
	
	/**
	 * Update note.
	 *
	 * @param note the note
	 */
	void updateNote(final Note note);
	
	/**
	 * Delete note by id.
	 *
	 * @param noteId the note id
	 */
	void deleteNoteById(final UUID noteId);
}
