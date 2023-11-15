package com.medilabo.disease.assessment.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AgeGenreDTOBean {

	private byte age;
	
	private String genre;
}
