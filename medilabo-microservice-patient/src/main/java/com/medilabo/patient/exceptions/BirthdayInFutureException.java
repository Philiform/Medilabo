package com.medilabo.patient.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BirthdayInFutureException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5919854133638559710L;

	public BirthdayInFutureException(String message) {
		super(message);
	}
}
