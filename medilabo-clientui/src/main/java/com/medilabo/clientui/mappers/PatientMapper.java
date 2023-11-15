package com.medilabo.clientui.mappers;

import org.mapstruct.Mapper;

import com.medilabo.clientui.beans.PatientBean;
import com.medilabo.clientui.dto.PatientDTO;

@Mapper(componentModel = "spring")
public interface PatientMapper {

	PatientBean fromPatientDTO(PatientDTO patientDTO);
	PatientDTO fromPatient(PatientBean patientBean);
	
}
