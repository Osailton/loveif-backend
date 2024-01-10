package com.amorif.exceptions;

import org.springframework.security.core.AuthenticationException;

/**
 * @author osailton
 */

public class UserNotFoundException extends AuthenticationException {

	private static final long serialVersionUID = 1L;
	
	public UserNotFoundException(String msg) {
		super(msg);
	}

}
