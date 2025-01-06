package com.amorif.exceptions;

public class RuleAlreadyCancelledOrAppliedException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public RuleAlreadyCancelledOrAppliedException(String msg) {
		super(msg);
	}
}
