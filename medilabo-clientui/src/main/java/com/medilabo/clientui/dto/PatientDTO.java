package com.medilabo.clientui.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientDTO {

	private Integer id;
	
	@NotEmpty
	private String lastName;
	
	@NotEmpty
	private String firstName;
}
