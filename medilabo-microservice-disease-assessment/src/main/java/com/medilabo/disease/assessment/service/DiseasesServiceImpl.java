package com.medilabo.disease.assessment.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.medilabo.disease.assessment.model.DiseaseTerms;
import com.medilabo.disease.assessment.model.Diseases;

// TODO: Auto-generated Javadoc
/**
 * The Class DiseasesServiceImpl.
 */
@Service
public class DiseasesServiceImpl implements DiseasesService {

	/** The list diseases. */
	private Diseases listDiseases = new Diseases();
	
	/**
	 * Instantiates a new diseases service impl.
	 */
	public DiseasesServiceImpl() {
		createData();
	}
	
	/**
	 * Creates the data.
	 */
	@Override
	public void createData() {

		List<String> listTermDiabete = List.of(
				"Hémoglobine A1C",
				"Microalbumine",
				"Taille",
				"Poids",
				"Fumeur",
				"Fumeuse",
				"Anormal",
				"Cholestérol",
				"Vertige",
				"Rechute",
				"Réaction",
				"Anticorps");
		
		List<String> listTermLeucemie = List.of("Globules blanc");
		
		listDiseases.getListDiseases().add(new DiseaseTerms("Diabète", listTermDiabete));
		listDiseases.getListDiseases().add(new DiseaseTerms("Leucémie", listTermLeucemie));
	}

	/**
	 * Gets the all terms by disease.
	 *
	 * @param disease the disease
	 * @return the all terms by disease
	 */
	@Override
	public List<String> getAllTermsByDisease(final String disease) {
		
		for(DiseaseTerms t : listDiseases.getListDiseases()) {
			if(t.getDisease().equals(disease)) return t.getListTerm();
		}
		
		return new ArrayList<>();
	}


}
