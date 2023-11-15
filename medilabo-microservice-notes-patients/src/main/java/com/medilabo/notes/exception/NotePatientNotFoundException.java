package com.medilabo.notes.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotePatientNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 209866080159536222L;

	public NotePatientNotFoundException(String message) {
		super(message);
	}

}
