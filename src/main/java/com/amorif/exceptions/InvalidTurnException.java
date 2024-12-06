package com.amorif.exceptions;

public class InvalidTurnException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public InvalidTurnException(String msg) {
		super(msg);
	}
}
