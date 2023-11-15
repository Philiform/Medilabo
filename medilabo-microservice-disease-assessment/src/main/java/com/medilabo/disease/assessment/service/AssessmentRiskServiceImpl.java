package com.medilabo.disease.assessment.service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.medilabo.disease.assessment.beans.AgeGenreDTOBean;
import com.medilabo.disease.assessment.enums.Genre;
import com.medilabo.disease.assessment.proxies.MicroserviceNotePatientProxy;
import com.medilabo.disease.assessment.proxies.MicroservicePatientProxy;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// TODO: Auto-generated Javadoc
/**
 * Instantiates a new assessment risk service impl.
 *
 * @param diseasesService the diseases service
 * @param patientProxy the patient proxy
 * @param notePatientProxy the note patient proxy
 */
@RequiredArgsConstructor
@Service

/** The Constant log. */
@Slf4j
public class AssessmentRiskServiceImpl implements AssessmentRiskService {

	/** The diseases service. */
	private final DiseasesService diseasesService;

	/** The patient proxy. */
	private final MicroservicePatientProxy patientProxy;
	
	/** The note patient proxy. */
	private final MicroserviceNotePatientProxy notePatientProxy;
	
	/**
	 * Gets the assessment.
	 *
	 * @param patientId the patient id
	 * @param disease the disease
	 * @return the assessment
	 */
	@Override
	public String getAssessment(final Integer patientId, final String disease) {
		if(patientProxy.isExistByPatientId(patientId) == false) return "";
		
		AgeGenreDTOBean ageAndGenrePatient = null;
		List<String> notesPatient = new ArrayList<>();
		List<String> termsToSearch = new ArrayList<>();

		notesPatient = notePatientProxy.listNotesByPatientId(patientId)
				.stream()
				.map(n -> {
					return n.getNote();
				})
				.collect(Collectors.toList());
		
		if(notesPatient.size() == 0) return "None";
		
		log.debug("notes: " + notesPatient);
		
		ageAndGenrePatient = patientProxy.getAgeAndGenreByPatientId(patientId);

		log.debug("ageAndGenrePatient: " + ageAndGenrePatient);
		
		termsToSearch = diseasesService.getAllTermsByDisease(disease);

		log.debug("termsToSearch: " + termsToSearch);
		
		return getAssessment(disease, ageAndGenrePatient, notesPatient, termsToSearch);
	}

	/**
	 * Gets the assessment.
	 *
	 * @param disease the disease
	 * @param ageGenreDTOBean the age genre DTO bean
	 * @param notesPatient the notes patient
	 * @param termsToSearch the terms to search
	 * @return the assessment
	 */
	private String getAssessment(String disease, AgeGenreDTOBean ageGenreDTOBean, List<String> notesPatient, List<String> termsToSearch) {
		int numberTerms = 0;
		Pattern pattern = null;
		Matcher matcher = null;
		String genre = "";
		
		for(String n : notesPatient) {
			for(String t : termsToSearch) {
				pattern = Pattern.compile("\\b%s(?!\\w)".format(t), Pattern.CASE_INSENSITIVE);
				matcher = pattern.matcher(n);

				while (matcher.find()) {
					numberTerms++;
					log.debug(t);
				}
			}
		}
		
		log.debug("numberTerms: " + numberTerms);			
		
		if(ageGenreDTOBean.getAge() >= 30) {
			if(numberTerms <= 1) return "None";
			else if(numberTerms >= 2 && numberTerms <= 5) return "Borderline";
			else if(numberTerms == 6 || numberTerms == 7) return "In Danger";
			else return "Early onset";
		} else { // age < 30
			genre = ageGenreDTOBean.getGenre();
			
			if(
				(
					genre.equals(Genre.MAN) && numberTerms <= 2
				) 
				||
				(
					genre.equals(Genre.WOMAN) && numberTerms <= 3  
				) 
			) return "None";
			else if(
				(
					genre.equals(Genre.MAN)
					&&
					(numberTerms == 3 || numberTerms == 4)
				) 
				||
				(
					genre.equals(Genre.WOMAN)
					&&
					(numberTerms >= 4 && numberTerms <= 6)  
				) 
			) return "In Danger";
			else return "Early onset";	
		}
	}

}
