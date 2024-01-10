package com.amorif.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * @author osailton
 * 
 *         Represents the collection in wich the students are located It must
 *         have an AnoLetivo as an id value
 */

@Entity
@Table(name = "turma")
public class Turma implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ano_letivo_id")
	private AnoLetivo anoLetivo;

	private String nome;

	private String descricao;
	
	@JsonIgnore
	@OneToMany(mappedBy = "turma")
	private Set<Pontuacao> pontuacao = new HashSet<>();

	public Turma() {

	}
	
	public Turma(Builder builder) {
		this.id = builder.id;
		this.anoLetivo = builder.anoLetivo;
		this.nome = builder.nome;
		this.descricao = builder.descricao;
		this.pontuacao = builder.pontuacao;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public AnoLetivo getAnoLetivo() {
		return anoLetivo;
	}

	public void setAnoLetivo(AnoLetivo anoLetivo) {
		this.anoLetivo = anoLetivo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Set<Pontuacao> getPontuacao() {
		return pontuacao;
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
		Turma other = (Turma) obj;
		return Objects.equals(id, other.id);
	}

	public static Builder builder() {
		return new Builder();
	}
	
	public static final class Builder {
		Long id;
		AnoLetivo anoLetivo;
		String nome;
		String descricao;
		Set<Pontuacao> pontuacao;
		
		public Builder id(Long id) {
			this.id = id;
			return this;
		}
		
		public Builder anoLetivo(AnoLetivo anoLetivo) {
			this.anoLetivo = anoLetivo;
			return this;
		}
		
		public Builder nome(String nome) {
			this.nome = nome;
			return this;
		}
		
		public Builder descricao(String descricao) {
			this.descricao = descricao;
			return this;
		}
		
		public Builder pontuacao(Set<Pontuacao> pontuacao) {
			this.pontuacao = pontuacao;
			return this;
		}
		
		public Turma build() {
			return new Turma(this);
		}
	}

}
