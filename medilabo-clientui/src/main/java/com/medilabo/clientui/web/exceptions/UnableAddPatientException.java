package com.medilabo.clientui.web.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class UnableAddPatientException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -789207952272081566L;

	public UnableAddPatientException(String message) {
		super(message);
	}
}
