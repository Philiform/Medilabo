package com.medilabo.disease.assessment.model;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Diseases {

	private List<DiseaseTerms> listDiseases = new ArrayList<>();

}