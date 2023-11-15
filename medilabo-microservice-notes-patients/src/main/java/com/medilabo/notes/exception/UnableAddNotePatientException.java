package com.medilabo.notes.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UnableAddNotePatientException extends RuntimeException {


	/**
	 * 
	 */
	private static final long serialVersionUID = 6709054966774478469L;

	public UnableAddNotePatientException(String message) {
		super(message);
	}

}
