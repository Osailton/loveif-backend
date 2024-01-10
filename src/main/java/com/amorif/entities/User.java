package com.amorif.entities;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;

/**
 * @author osailton
 * 
 *         User class that implements UserDetails Its used for controlling who
 *         has already logged through SUAP at least once
 */

@Entity
@Table(name = "amorif_usuario")
public class User implements UserDetails {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nome;

	@Column(unique = true)
	private String matricula;
	
	private String email;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "usuario_funcoes",
		joinColumns = @JoinColumn(name = "usuario_id"),
		inverseJoinColumns = @JoinColumn(name = "funcao_id"))
	private Set<Role> funcoes = new HashSet<>();

	public User() {

	}

	public User(Builder builder) {
		this.id = builder.id;
		this.nome = builder.nome;
		this.matricula = builder.matricula;
		this.funcoes = builder.funcoes;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public Set<Role> getFuncoes() {
		return funcoes;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return funcoes;
	}

	@Override
	public String getPassword() {
		return null;
	}

	@Override
	public String getUsername() {
		return this.matricula;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
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
		User other = (User) obj;
		return Objects.equals(id, other.id);
	}

	public static Builder builder() {
		return new Builder();
	}

	public static final class Builder {
		Long id;
		String nome;
		String matricula;
		String email;
		Set<Role> funcoes = new HashSet<>();

		public Builder id(Long id) {
			this.id = id;
			return this;
		}

		public Builder nome(String nome) {
			this.nome = nome;
			return this;
		}

		public Builder matricula(String matricula) {
			this.matricula = matricula;
			return this;
		}
		
		public Builder email(String email) {
			this.email = email;
			return this;
		}
		
		public Builder funcoes(Set<Role> funcoes) {
			this.funcoes = funcoes;
			return this;
		}
		
		public Builder funcao(Role role) {
			this.funcoes.add(role);
			return this;
		}

		public User build() {
			return new User(this);
		}
	}

}