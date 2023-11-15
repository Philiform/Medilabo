package com.medilabo.disease.assessment.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.medilabo.disease.assessment.model.DiseaseTerms;
import com.medilabo.disease.assessment.model.Diseases;

@ExtendWith(MockitoExtension.class)
class AssessmentRiskServiceTest {

	@InjectMocks
	private DiseasesServiceImpl service;

	@Mock
	private DiseasesService diseasesService;

	private static List<String> listTermDiabete = new ArrayList<>();
	private static List<String> listTermLeucemie = new ArrayList<>();
	private static Diseases listDiseases = new Diseases();
	
	@BeforeAll
	static void setUp() throws Exception {
		listTermDiabete = List.of(
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
		
		listTermLeucemie.add("Globules blanc");
		
		listDiseases.getListDiseases().add(new DiseaseTerms("Diabète", listTermDiabete));
		listDiseases.getListDiseases().add(new DiseaseTerms("Leucémie", listTermLeucemie));
	}

	@Test
	void test_GivenDiseaseDiabete_WhenGetAllTermsByDisease_ThenReturnListTermDiabete() {
		List<String> response = service.getAllTermsByDisease("Diabète");

		assertThat(response.size()).isEqualTo(listTermDiabete.size());
		assertThat(response.get(0)).isEqualTo(listTermDiabete.get(0));
	}

	@Test
	void test_GivenDiseaseLeucemie_WhenGetAllTermsByDisease_ThenReturnListTermLeucemie() {
		List<String> response = service.getAllTermsByDisease("Leucémie");

		assertThat(response.size()).isEqualTo(listTermLeucemie.size());
		assertThat(response.get(0)).isEqualTo(listTermLeucemie.get(0));
	}

	@Test
	void test_GivenBadDisease_WhenGetAllTermsByDisease_ThenReturnListTermDiabete() {
		List<String> response = service.getAllTermsByDisease("Cancer gorge");

		assertThat(response.size()).isEqualTo(0);
	}

}
