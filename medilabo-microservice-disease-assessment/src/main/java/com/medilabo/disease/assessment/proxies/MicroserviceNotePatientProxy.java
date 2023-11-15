package com.medilabo.disease.assessment.proxies;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.medilabo.disease.assessment.beans.NoteBean;

// TODO: Auto-generated Javadoc
/**
 * The Interface MicroserviceNotePatientProxy.
 */
@FeignClient(name = "microservice-notes-patients")
public interface MicroserviceNotePatientProxy {

	/**
	 * List notes by patient id.
	 *
	 * @param patientId the patient id
	 * @return the list
	 */
	@GetMapping(value = "/patient/{patientId}/notePatient/listNotes")
	List<NoteBean> listNotesByPatientId(@PathVariable final Integer patientId);
	
}
