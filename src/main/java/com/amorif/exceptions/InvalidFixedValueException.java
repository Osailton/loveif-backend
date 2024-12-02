package com.amorif.exceptions;

public class InvalidFixedValueException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	public InvalidFixedValueException(String msg) {
		super(msg);
	}
	
}
