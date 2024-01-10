package com.amorif.dto.request;

import java.io.Serializable;

/**
 * @author osailton
 */

public class RegisterDtoRequest implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String token;

	public RegisterDtoRequest() {
		
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
