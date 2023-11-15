package com.medilabo.patient.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class UnableAddPatientException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1317810554387042671L;

	public UnableAddPatientException(String message) {
		super(message);
	}
}
