package com.medilabo.clientui.proxies;

import java.util.List;
import java.util.Optional;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.medilabo.clientui.beans.NoteBean;
import com.medilabo.clientui.beans.NotePatientBean;
import com.medilabo.clientui.beans.UuidClassBean;

import jakarta.validation.Valid;

// TODO: Auto-generated Javadoc
/**
 * The Interface MicroserviceNotePatientProxy.
 */
@FeignClient(name = "microservice-notes-patients")
public interface MicroserviceNotePatientProxy {

	// ======= NotePatient =======

	/**
	 * Gets the note patient by patient id.
	 *
	 * @param patientId the patient id
	 * @return the note patient by patient id
	 */
	@GetMapping(value = "/patient/{patientId}/notePatient")
	Optional<NotePatientBean> getNotePatientByPatientId(@PathVariable final Integer patientId);
		
	/**
	 * Save note patient.
	 *
	 * @param notePatientBean the note patient bean
	 * @return the response entity
	 */
	@PostMapping(value = "/notePatient/save")
	ResponseEntity<NotePatientBean> saveNotePatient(@RequestBody final NotePatientBean notePatientBean);
	
	/**
	 * Update note patient.
	 *
	 * @param notePatientBean the note patient bean
	 */
	@PutMapping(value = "/notePatient/update")
	void updateNotePatient(@RequestBody final NotePatientBean notePatientBean);

	/**
	 * Delete note patient by patient id.
	 *
	 * @param patientId the patient id
	 */
	@DeleteMapping(value = "/patient/{patientId}/notePatient/delete")
	void deleteNotePatientByPatientId(@PathVariable final Integer patientId);

	// ======= Note =======

	/**
	 * List notes by patient id.
	 *
	 * @param patientId the patient id
	 * @return the list
	 */
	@GetMapping(value = "/patient/{patientId}/notePatient/listNotes")
	List<NoteBean> listNotesByPatientId(@PathVariable final Integer patientId);
	
	/**
	 * Gets the note by id.
	 *
	 * @param noteId the note id
	 * @return the note by id
	 */
	@PostMapping(value = "/notePatient/note")
	Optional<NoteBean> getNoteById(@RequestBody final UuidClassBean noteId);

	/**
	 * Save note by patient id.
	 *
	 * @param patientId the patient id
	 * @param noteBean the note bean
	 * @return the response entity
	 */
	@PostMapping(value = "/patient/{patientId}/notePatient/note/save")
	ResponseEntity<NotePatientBean> saveNoteByPatientId(@PathVariable final Integer patientId, @RequestBody @Valid final NoteBean noteBean);
	
	/**
	 * Update note.
	 *
	 * @param note the note
	 */
	@PutMapping(value = "/notePatient/note/update")
	void updateNote(@RequestBody @Valid final NoteBean note);
	
	/**
	 * Delete note by id.
	 *
	 * @param noteId the note id
	 */
	@DeleteMapping(value = "/notePatient/note/delete")
	public void deleteNoteById(@RequestBody final UuidClassBean noteId);
	
}
