package com.amorif.exceptions;

import org.springframework.security.core.AuthenticationException;

/**
 * @author osailton
 */

public class RuleNotFoundException extends AuthenticationException {

	private static final long serialVersionUID = 1L;
	
	public RuleNotFoundException(String msg) {
		super(msg);
	}

}
