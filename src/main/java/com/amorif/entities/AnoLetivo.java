package com.amorif.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * @author osailton
 * 
 *         This entity represents a year in wich the Turma are located
 */

@Entity
@Table(name = "ano_letivo")
public class AnoLetivo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true)
	private Integer ano;

	@OneToMany(mappedBy = "anoLetivo")
	private Set<Turma> turmas = new HashSet<>();

	private boolean aberto;

	public AnoLetivo() {

	}

	public AnoLetivo(Builder builder) {
		this.id = builder.id;
		this.ano = builder.ano;
		this.turmas = builder.turmas;
		this.aberto = builder.aberto;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getAno() {
		return ano;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}

	public Set<Turma> getTurmas() {
		return turmas;
	}

	public boolean isAberto() {
		return aberto;
	}

	public void setAberto(boolean aberto) {
		this.aberto = aberto;
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
		AnoLetivo other = (AnoLetivo) obj;
		return Objects.equals(id, other.id);
	}

	public static Builder builder() {
		return new Builder();
	}

	public static final class Builder {
		Long id;
		Integer ano;
		Set<Turma> turmas = new HashSet<>();
		boolean aberto;

		public Builder id(Long id) {
			this.id = id;
			return this;
		}

		public Builder ano(Integer ano) {
			this.ano = ano;
			return this;
		}

		public Builder turmas(Set<Turma> turmas) {
			this.turmas = turmas;
			return this;
		}

		public Builder aberto(boolean aberto) {
			this.aberto = aberto;
			return this;
		}

		public AnoLetivo build() {
			return new AnoLetivo(this);
		}
	}
}
