package com.medilabo.patient.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PatientNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6422654053862324202L;

	public PatientNotFoundException(String message) {
		super(message);
	}

}
