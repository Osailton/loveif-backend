package com.amorif.dto.request;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author osailton
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class RegisterDtoRequest implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@JsonProperty("access_token")
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
