package com.amorif.dto.response;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author osailton
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthenticationDtoResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty("access_token")
	private String accessToken;

	@JsonProperty("refresh_token")
	private String refreshToken;
	
	private String matricula;
	
	private String nome;
	
	private List<String> roles;

	public AuthenticationDtoResponse() {

	}

	private AuthenticationDtoResponse(Builder builder) {
		this.accessToken = builder.accessToken;
		this.refreshToken = builder.refreshToken;
		this.matricula = builder.matricula;
		this.nome = builder.nome;
		this.roles = builder.roles;
	}
	
	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public static Builder builder() {
		return new Builder();
	};

	public static final class Builder {
		String accessToken;
		String refreshToken;
		String matricula;
		String nome;
		List<String> roles;

		public Builder accessToken(String accessToken) {
			this.accessToken = accessToken;
			return this;
		}

		public Builder refreshToken(String refreshToken) {
			this.refreshToken = refreshToken;
			return this;
		}
		
		public Builder matricula(String matricula) {
			this.matricula = matricula;
			return this;
		}
		
		public Builder nome(String nome) {
			this.nome = nome;
			return this;
		}
		
		public Builder roles(List<String> roles) {
			this.roles = roles;
			return this;
		}

		public AuthenticationDtoResponse build() {
			return new AuthenticationDtoResponse(this);
		}
	}
}
