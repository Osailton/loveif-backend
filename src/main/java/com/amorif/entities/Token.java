package com.amorif.entities;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.FetchType;

/**
 * @author osailton
 */

@Entity
@Table(name = "token")
public class Token implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true, length = 512)
	private String token;

	private String tokenType = "BEARER";

	private boolean revoked;

	private boolean expired;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	public Token() {

	}

	public Token(Builder builder) {
		this.id = builder.id;
		this.token = builder.token;
		this.tokenType = builder.tokenType;
		this.revoked = builder.revoked;
		this.expired = builder.expired;
		this.user = builder.user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	public boolean isRevoked() {
		return revoked;
	}

	public void setRevoked(boolean revoked) {
		this.revoked = revoked;
	}

	public boolean isExpired() {
		return expired;
	}

	public void setExpired(boolean expired) {
		this.expired = expired;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Token other = (Token) obj;
		return Objects.equals(id, other.id);
	}
	
	public static Builder builder() {
		return new Builder();
	}

	public static final class Builder {
		Long id;
		String token;
		String tokenType = "BEARER";
		boolean revoked;
		boolean expired;
		User user;

		public Builder id(Long id) {
			this.id = id;
			return this;
		}

		public Builder token(String token) {
			this.token = token;
			return this;
		}

		public Builder tokenType(String tokenType) {
			this.tokenType = tokenType;
			return this;
		}

		public Builder revoked(boolean revoked) {
			this.revoked = revoked;
			return this;
		}

		public Builder expired(boolean expired) {
			this.expired = expired;
			return this;
		}

		public Builder user(User user) {
			this.user = user;
			return this;
		}

		public Token build() {
			return new Token(this);
		}
	}

}
