package com.amorif.exceptions;

public class InvalidVariableValueException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	public InvalidVariableValueException(String msg) {
		super(msg);
	}
	
}
