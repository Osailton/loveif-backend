package com.amorif.exceptions;

import org.springframework.security.core.AuthenticationException;

public class InvalidArgumentException extends AuthenticationException  {

	private static final long serialVersionUID = 1L;
	
	public InvalidArgumentException(String msg) {
		super(msg);
	}

}
