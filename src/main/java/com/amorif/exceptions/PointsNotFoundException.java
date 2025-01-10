package com.amorif.exceptions;

import org.springframework.security.core.AuthenticationException;

/**
 * @author osailton
 */

public class PointsNotFoundException extends AuthenticationException {

	private static final long serialVersionUID = 1L;
	
	public PointsNotFoundException(String msg) {
		super(msg);
	}

}
