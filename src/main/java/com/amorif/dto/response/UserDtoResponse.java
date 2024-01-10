package com.amorif.dto.response;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author osailton
 */

public class UserDtoResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private String matricula;
	private String username;
	private String email;

	public UserDtoResponse() {

	}

	public UserDtoResponse(Builder builder) {
		this.matricula = builder.matricula;
		this.username = builder.username;
		this.email = builder.email;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public int hashCode() {
		return Objects.hash(matricula);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserDtoResponse other = (UserDtoResponse) obj;
		return Objects.equals(matricula, other.matricula);
	}

	public static Builder builder() {
		return new Builder();
	};

	public static final class Builder {
		String matricula;
		String username;
		String email;

		public Builder matricula(String matricula) {
			this.matricula = matricula;
			return this;
		}

		public Builder username(String username) {
			this.username = username;
			return this;
		}

		public Builder email(String email) {
			this.email = email;
			return this;
		}

		public UserDtoResponse build() {
			return new UserDtoResponse(this);
		}
	}

}
