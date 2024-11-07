package com.amorif.entities;

import java.util.List;
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
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author osailton
 */

@Builder
@Getter
@Setter
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
	private Set<User> usuarios;
	
	@ManyToMany(mappedBy = "roles")
	@JsonIgnore
    private List<Regra> regras;

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

	public Role(Long id, String name, Set<User> usuarios, List<Regra> regras) {
		super();
		this.id = id;
		this.name = name;
		this.usuarios = usuarios;
		this.regras = regras;
	}
	
	public Role() {
		super();
	}

}
