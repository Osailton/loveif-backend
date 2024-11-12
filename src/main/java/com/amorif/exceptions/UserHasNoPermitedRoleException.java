package com.amorif.exceptions;

public class UserHasNoPermitedRoleException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserHasNoPermitedRoleException(String message) {
        super(message);
    }
}
