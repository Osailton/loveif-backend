package com.amorif.exceptions;

public class InvalidSchoolRegistrationException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public InvalidSchoolRegistrationException(String msg) {
		super(msg);
	}
	
}
