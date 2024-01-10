package com.amorif.entities;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

/**
 * @author osailton
 */

@Entity
@Table(name = "funcao")
public class Role implements GrantedAuthority {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true)
	private String name;
	
	@JsonIgnore
	@ManyToMany(mappedBy = "funcoes")
	private Set<User> usuarios = new HashSet<>();

	public Role() {
		
	}
	
	private Role(Builder builder) {
		this.id = builder.id;
		this.name = builder.name;
		this.usuarios = builder.usuarios;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<User> getUsuarios() {
		return usuarios;
	}

	@Override
	public String getAuthority() {
		return this.name;
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
		Role other = (Role) obj;
		return id == other.id;
	}
	
	public static Builder builder() {
		return new Builder();
	};
	
	public static final class Builder {
		Long id;
		String name;
		Set<User> usuarios = new HashSet<>();
		
		public Builder id(Long id) {
			this.id = id;
			return this;
		}
		
		public Builder name(String name) {
			this.name = name;
			return this;
		}
		
		public Builder usuarios(Set<User> usuarios) {
			this.usuarios = usuarios;
			return this;
		}
		
		public Role build() {
			return new Role(this);
		}
	}
}
