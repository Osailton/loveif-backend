package com.amorif.exceptions;

import org.springframework.security.core.AuthenticationException;

/**
 * @author osailton
 */

public class UserAlreadyExistsException extends AuthenticationException {

	private static final long serialVersionUID = 1L;
	
	public UserAlreadyExistsException(String msg) {
		super(msg);
	}

}
