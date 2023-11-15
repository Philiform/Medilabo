package com.medilabo.clientui.web.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.medilabo.clientui.beans.NoteBean;
import com.medilabo.clientui.beans.NotePatientBean;
import com.medilabo.clientui.beans.PatientBean;
import com.medilabo.clientui.dto.ListNotesDTO;
import com.medilabo.clientui.dto.NoteDTO;
import com.medilabo.clientui.dto.PatientDTO;
import com.medilabo.clientui.services.DiseaseAssessmentService;
import com.medilabo.clientui.services.NotePatientService;
import com.medilabo.clientui.services.PatientService;
import com.medilabo.clientui.web.exceptions.UnableAddPatientException;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// TODO: Auto-generated Javadoc
/**
 * The Class ClientController.
 */
@Controller

/**
 * Instantiates a new client controller.
 *
 * @param patientService the patient service
 * @param notePatientService the note patient service
 * @param diseaseAssessmentService the disease assessment service
 */
@RequiredArgsConstructor

/** The Constant log. */
@Slf4j
public class ClientController {

	/** The patient service. */
	private final PatientService patientService;
	
	/** The note patient service. */
	private final NotePatientService notePatientService;
	
	/** The disease assessment service. */
	private final DiseaseAssessmentService diseaseAssessmentService;
	
	/** The username. */
	private String username;

	/**
	 * Gets the patient infos by patient id.
	 *
	 * @param model the model
	 * @param patientId the patient id
	 * @return the patient infos by patient id
	 */
	@GetMapping("/patient/{patientId}")
	public String getPatientInfosByPatientId(Model model, @PathVariable final Integer patientId) {		
		log.info("getPatientInfosByPatientId");

		return getPatientInfosByPatientIdWithNote(model, patientId, new NoteDTO());
	}

	/**
	 * Adds the patient form.
	 *
	 * @param model the model
	 * @return the string
	 */
	@GetMapping("/patient/add")
	public String addPatientForm(Model model) {
		log.info("addPatientForm");

		model.addAttribute("patient", new PatientBean());
		
		return "patient/add";
	}

	/**
	 * Save patient.
	 *
	 * @param patientBean the patient bean
	 * @param bindingResult the binding result
	 * @return the string
	 */
	@PostMapping("/patient/save")
	public String savePatient(@ModelAttribute("patient") @Valid final PatientBean patientBean, BindingResult bindingResult) {
		log.info("savePatient");
		log.debug("patientBean = " + patientBean);

		try {
			if (bindingResult.hasErrors() ||
					!patientService.isBirthdayValid(patientBean.getBirthday())) {
				return "redirect:/patient/add";
			}

			Integer patientBeanId = patientService.savePatient(patientBean);
			
			if(patientBeanId.equals(-1)) throw new UnableAddPatientException("Unable to add new patient");

			NotePatientBean notePatientBean = new NotePatientBean();
			notePatientBean.setPatientId(patientBeanId);
			
			ResponseEntity<NotePatientBean> responseNotePatientBean = notePatientService.saveNotePatient(notePatientBean);
			
			if(responseNotePatientBean.getStatusCode() != HttpStatus.CREATED) {
				throw new UnableAddPatientException("Unable to create notes for this patient");
			}
		} catch (Exception e) {
			log.error(e.toString());
		}

		return "redirect:/patient/list";
	}

	/**
	 * Update patient.
	 *
	 * @param patientBean the patient bean
	 * @param bindingResult the binding result
	 * @return the string
	 */
	@PostMapping("/patient/update")
	public String updatePatient(@ModelAttribute("patient") @Valid final PatientBean patientBean, BindingResult bindingResult) {
		log.info("updatePatient");
		log.debug("patientBean = " + patientBean);

		try {
			if (!bindingResult.hasErrors() &&
					patientService.isBirthdayValid(patientBean.getBirthday())) {
				patientService.updatePatient(patientBean);
			}
		} catch (Exception e) {
			log.error(e.toString());
		}

		return "redirect:/patient/" + patientBean.getId();
	}

	/**
	 * Delete patient by patient id.
	 *
	 * @param patientId the patient id
	 * @return the string
	 */
	@GetMapping("/patient/{patientId}/delete")
	public String deletePatientByPatientId(@PathVariable final int patientId) {
		log.info("deletePatientByPatientId");

		patientService.deletePatientByPatientId(patientId);
		notePatientService.deleteNotePatientByPatientId(patientId);

		return "redirect:/patient/list";
	}

	/**
	 * List patients.
	 *
	 * @param model the model
	 * @return the string
	 */
	@GetMapping("/patient/list")
	public String listPatients(Model model) {
		log.info("listPatients");

		List<PatientDTO> patients = patientService.listPatients();

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		username = ((UserDetails)principal).getUsername();
		
		log.debug("username = " + username);

		model.addAttribute("patients", patients);
		model.addAttribute("username", username);

		return "patient/list";
	}

	/**
	 * Save note.
	 *
	 * @param noteDTO the note DTO
	 * @return the string
	 */
	@PostMapping(value = "/patient/note/save")
	public String saveNote(@ModelAttribute("noteDTO") @Valid final NoteDTO noteDTO/*, BindingResult bindingResult*/) {
		log.info("saveNote");
		log.debug("noteDTO = " + noteDTO);

		notePatientService.saveNoteByPatientId(noteDTO);

		return "redirect:/patient/" + noteDTO.getPatientId();
	}

	/**
	 * Update note by patient id.
	 *
	 * @param model the model
	 * @param patientId the patient id
	 * @param noteId the note id
	 * @return the string
	 */
	@GetMapping("/patient/{patientId}/note/{noteId}/update")
	public String updateNoteByPatientId(Model model, @PathVariable final Integer patientId, @PathVariable final UUID noteId) {
		log.info("updateNoteByPatientId");

		return getPatientInfosByPatientIdWithNote(model, patientId, new NoteDTO(null, notePatientService.getNoteById(noteId).get()));
	}

	/**
	 * Delete note by id.
	 *
	 * @param patientId the patient id
	 * @param noteId the note id
	 * @return the string
	 */
	@GetMapping("/patient/{patientId}/note/{noteId}/delete")
	public String deleteNoteById(@PathVariable final Integer patientId, @PathVariable final UUID noteId) {
		log.info("deleteNoteById");

		notePatientService.deleteNoteById(noteId);

		return "redirect:/patient/" + patientId;
	}

	/**
	 * Exception handler.
	 *
	 * @param e the e
	 * @return the string
	 */
	@ExceptionHandler(Exception.class)
	public String exceptionHandler(Exception e) {
		log.info("exceptionHandler");
		log.debug(e.getMessage());
		
		return "redirect:/patient/list";
	}

	/**
	 * Gets the patient infos by patient id with note.
	 *
	 * @param model the model
	 * @param patientId the patient id
	 * @param noteDTO the note DTO
	 * @return the patient infos by patient id with note
	 */
	private String getPatientInfosByPatientIdWithNote(Model model, final Integer patientId, final NoteDTO noteDTO) {		
		log.info("getPatientInfosByPatientIdWithNote");
		
		Optional<PatientBean> patient = patientService.getPatientById(patientId);
		
		if(patient.isEmpty()) return "patient/list";

		noteDTO.setPatientId(patient.get().getId());

		String disease = "Diabète";
		String assessment = diseaseAssessmentService.getAssessment(patientId, disease);
		
		List<NoteBean> notes = notePatientService.listNotesByPatientId(patientId);
		
		ListNotesDTO listNotesDTO = new ListNotesDTO();
		listNotesDTO.setPatientId(patientId);
		listNotesDTO.setNotes(notes);

		model.addAttribute("disease", disease);
		model.addAttribute("assessment", assessment);
		model.addAttribute("patient", patient);
		model.addAttribute("listNotesDTO", listNotesDTO);
		model.addAttribute("noteDTO", noteDTO);

		log.debug("disease = " + disease);
		log.debug("assessment = " + assessment);
		log.debug("patientBean = " + patient);
		log.debug("listNotesDTO = " + listNotesDTO);
		log.debug("noteDTO = " + noteDTO);

		return "patient/infos";
	}

}
