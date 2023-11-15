package com.medilabo.notes.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.medilabo.notes.model.Note;
import com.medilabo.notes.model.NotePatient;
import com.medilabo.notes.model.UuidClass;
import com.medilabo.notes.service.NotePatientService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// TODO: Auto-generated Javadoc
/**
 * The Class NotePatientController.
 */
@RestController

/**
 * Instantiates a new note patient controller.
 *
 * @param notePatientService the note patient service
 */
@RequiredArgsConstructor

/** The Constant log. */
@Slf4j
public class NotePatientController {

	/** The note patient service. */
	private final NotePatientService notePatientService;
	
	/**
	 * Gets the note patient by patient id.
	 *
	 * @param patientId the patient id
	 * @return the note patient by patient id
	 */
	//======= NotePatient =======
	@GetMapping(value = "/patient/{patientId}/notePatient")
	public Optional<NotePatient> getNotePatientByPatientId(@PathVariable final Integer patientId) {
		log.info("getNotePatientByPatientId");
		
		return notePatientService.getNotePatientByPatientId(patientId);
	}
		
	/**
	 * Save note patient.
	 *
	 * @param notePatient the note patient
	 * @return the response entity
	 */
	@PostMapping(value = "/notePatient/save")
	public ResponseEntity<NotePatient> saveNotePatient(@RequestBody @Valid final NotePatient notePatient) {
		log.info("saveNotePatient");
		
		return new ResponseEntity<NotePatient>(notePatientService.saveNotePatient(notePatient), HttpStatus.CREATED);
	}
	
	/**
	 * Update note patient.
	 *
	 * @param notePatient the note patient
	 */
	@PutMapping(value = "/notePatient/update")
	public void updateNotePatient(@RequestBody @Valid final NotePatient notePatient) {
		log.info("updateNotePatient");
		
		notePatientService.updateNotePatient(notePatient);
	}
	
	/**
	 * Delete note patient by patient id.
	 *
	 * @param patientId the patient id
	 */
	@DeleteMapping(value = "/patient/{patientId}/notePatient/delete")
	public void deleteNotePatientByPatientId(@PathVariable final Integer patientId) {
		log.info("deleteNotePatientByPatientId");
		
		notePatientService.deleteNotePatientByPatientId(patientId);
	}
	
	/**
	 * List notes by patient id.
	 *
	 * @param patientId the patient id
	 * @return the list
	 */
	//======= Note =======
	@GetMapping(value = "/patient/{patientId}/notePatient/listNotes")
	public List<Note> listNotesByPatientId(@PathVariable final Integer patientId) {	
		log.info("listNotesByPatientId");
		
		return notePatientService.listNotesByPatientId(patientId);
	}
	
	/**
	 * Gets the note by id.
	 *
	 * @param noteId the note id
	 * @return the note by id
	 */
	@PostMapping(value = "/notePatient/note")
	public Optional<Note> getNoteById(@RequestBody final UuidClass noteId) {
		log.info("getNoteById");
		
		return notePatientService.getNoteById(noteId.getId());
	}

	/**
	 * Save note by patient id.
	 *
	 * @param patientId the patient id
	 * @param note the note
	 * @return the response entity
	 */
	@PostMapping(value = "/patient/{patientId}/notePatient/note/save")
	public ResponseEntity<NotePatient> saveNoteByPatientId(@PathVariable final Integer patientId, @RequestBody @Valid final Note note) {
		log.info("saveNoteByPatientId");
		
		return new ResponseEntity<NotePatient>(notePatientService.saveNoteByPatientId(patientId, note), HttpStatus.CREATED);
	}
	
	/**
	 * Update note.
	 *
	 * @param note the note
	 */
	@PutMapping(value = "/notePatient/note/update")
	public void updateNote(@RequestBody @Valid final Note note) {
		log.info("updateNote");
		
		notePatientService.updateNote(note);
	}
	
	/**
	 * Delete note by id.
	 *
	 * @param noteId the note id
	 */
	@DeleteMapping(value = "/notePatient/note/delete")
	public void deleteNoteById(@RequestBody final UuidClass noteId) {
		log.info("deleteNoteById");
		
		notePatientService.deleteNoteById(noteId.getId());
	}
	
	/**
	 * Exception handler.
	 *
	 * @param e the e
	 * @return the response entity
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> exceptionHandler(Exception e) {
		log.info("exceptionHandler");
		log.debug(e.getMessage());
		
		return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
