package com.amorif.exceptions;

public class TurmaNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public TurmaNotFoundException(String msg) {
		super(msg);
	}
}
