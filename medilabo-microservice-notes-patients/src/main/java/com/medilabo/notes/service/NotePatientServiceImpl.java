package com.medilabo.notes.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.medilabo.notes.dao.NotePatientDao;
import com.medilabo.notes.exception.NotePatientNotFoundException;
import com.medilabo.notes.exception.UnableAddNotePatientException;
import com.medilabo.notes.model.Note;
import com.medilabo.notes.model.NotePatient;

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
 * @param dao the dao
 */
@RequiredArgsConstructor

/** The Constant log. */
@Slf4j
public class NotePatientServiceImpl implements NotePatientService {

	/** The dao. */
	private final NotePatientDao dao;

	/**
	 * Gets the note patient by patient id.
	 *
	 * @param patientId the patient id
	 * @return the note patient by patient id
	 */
	//======= NotePatient =======
	@Override
	public Optional<NotePatient> getNotePatientByPatientId(final Integer patientId) {
		Optional<NotePatient> notePatient = dao.findByPatientId(patientId);
		
		if(!notePatient.isPresent()) throw new NotePatientNotFoundException("Notes for this patient not found.");
		
		return notePatient;
	}

	/**
	 * Save note patient.
	 *
	 * @param notePatient the note patient
	 * @return the note patient
	 */
	@Override
	public NotePatient saveNotePatient(final NotePatient notePatient) {
		NotePatient newNotePatient = dao.save(notePatient);
		
		if(newNotePatient == null) throw new UnableAddNotePatientException("Unable to add notePatient for this patient");
		
		return newNotePatient;
	}

	/**
	 * Update note patient.
	 *
	 * @param notePatient the note patient
	 */
	@Override
	public void updateNotePatient(final NotePatient notePatient) {
		dao.save(notePatient);
	}

	/**
	 * Delete note patient by patient id.
	 *
	 * @param patientId the patient id
	 */
	@Override
	public void deleteNotePatientByPatientId(final Integer patientId) {
		dao.delete(getNotePatientByPatientId(patientId).get());
	}

	/**
	 * List notes by patient id.
	 *
	 * @param patientId the patient id
	 * @return the list
	 */
	//======= Note =======
	@Override
	public List<Note> listNotesByPatientId(final Integer patientId) {
		log.debug("listNotesByPatientId");
		
		Optional<NotePatient> notePatient = dao.findByPatientId(patientId);
		
		if(!notePatient.isPresent()) return new ArrayList<>();
		
		log.debug("listNote = " + notePatient.get().getListNote());
	
		return notePatient.get().getListNote();
	}

	/**
	 * Gets the note by id.
	 *
	 * @param noteId the note id
	 * @return the note by id
	 */
	@Override
	public Optional<Note> getNoteById(UUID noteId) {
		Optional<NotePatient> notePatient = getNotePatientByNoteId(noteId);

		return getNoteByNoteId(notePatient.get().getListNote(), noteId);
	}

	/**
	 * Save note by patient id.
	 *
	 * @param patientId the patient id
	 * @param note the note
	 * @return the note patient
	 */
	@Override
	public NotePatient saveNoteByPatientId(final Integer patientId, final Note note) {
		log.debug("saveNoteByPatientId");
		log.debug("patientId = " + patientId);
		log.debug("note = " + note);
		
		Optional<NotePatient> notePatient = getNotePatientByPatientId(patientId);
	
		log.debug("notePatient = " + notePatient);
		
		UUID newUuid = UUID.randomUUID();
		
		while(!dao.findPatientIdByNoteId(newUuid.toString()).isEmpty()) {
			newUuid = UUID.randomUUID();
		}

		note.setId(newUuid);

		log.debug("note = " + note);
		
		notePatient.get().getListNote().add(note);
		
		log.debug("notePatient = " + notePatient);
		
		return saveNotePatient(notePatient.get());
	}

	/**
	 * Update note.
	 *
	 * @param note the note
	 */
	@Override
	public void updateNote(final Note note) {
		log.debug("updateNote");
		
		Optional<NotePatient> notePatient = getNotePatientByNoteId(note.getId());

		Optional<Note> noteFind = getNoteByNoteId(notePatient.get().getListNote(), note.getId());
		
		int noteIndex = notePatient.get().getListNote().indexOf(noteFind.get());
		
		noteFind.get().setNote(note.getNote());
		
		notePatient.get().getListNote().set(noteIndex, noteFind.get());

		log.debug("notePatient = " + notePatient);

		updateNotePatient(notePatient.get());
	}

	/**
	 * Delete note by id.
	 *
	 * @param noteId the note id
	 */
	@Override
	public void deleteNoteById(UUID noteId) {
		Optional<NotePatient> notePatient = getNotePatientByNoteId(noteId);

		Optional<Note> noteFind = getNoteByNoteId(notePatient.get().getListNote(), noteId);
		
		if(!noteFind.isEmpty()) {
			notePatient.get().getListNote().remove(noteFind.get());		

			updateNotePatient(notePatient.get());
		}
	}

	/**
	 * Gets the note patient by note id.
	 *
	 * @param noteId the note id
	 * @return the note patient by note id
	 */
	private Optional<NotePatient> getNotePatientByNoteId(UUID noteId) {
		Optional<NotePatient> notePatient = dao.findNotePatientByNoteId(noteId.toString());

		log.debug("notePatient = " + notePatient);

		if(notePatient.isEmpty()) throw new NotePatientNotFoundException("Note searched for this patient was not found.");

		return notePatient;
	}

	/**
	 * Gets the note by note id.
	 *
	 * @param listNotes the list notes
	 * @param noteId the note id
	 * @return the note by note id
	 */
	private Optional<Note> getNoteByNoteId(List<Note> listNotes, UUID noteId) {
		Optional<Note> noteFind = Optional.empty();
		
		for(Note n : listNotes) {
			if(n.getId().equals(noteId)) {
				noteFind = Optional.of(n);

				log.debug("noteFind = " + n);
				break;
			}
		}
		return noteFind;
	}
}
