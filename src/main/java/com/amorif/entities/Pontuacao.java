package com.amorif.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

/**
 * @author osailton
 * 
 *         Entity responsible to keep all point registers Each Turma has its own
 *         points
 */

@Entity
@IdClass(PontuacaoID.class)
@Table(name = "pontuacao")
public class Pontuacao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private Integer contador;

	@Id
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "turma_id")
	private Turma turma;

	@Enumerated(EnumType.STRING)
	private PontuacaoOperationEnum operacao = PontuacaoOperationEnum.SUM;

	private Integer pontos;

	private String descricao;

	private boolean aplicado;

	private boolean anulado;

	@Temporal(TemporalType.TIMESTAMP)
	private Date data;

	public Pontuacao() {

	}

	public Pontuacao(Builder builder) {
		this.contador = builder.contador;
		this.turma = builder.turma;
		this.operacao = builder.operacao;
		this.pontos = builder.pontos;
		this.descricao = builder.descricao;
		this.aplicado = builder.aplicado;
		this.anulado = builder.anulado;
		this.data = builder.data;
	}

	public Integer getContador() {
		return contador;
	}

	public void setContador(Integer contador) {
		this.contador = contador;
	}

	public Turma getTurma() {
		return turma;
	}

	public void setTurma(Turma turma) {
		this.turma = turma;
	}

	public PontuacaoOperationEnum getOperacao() {
		return operacao;
	}

	public void setOperacao(PontuacaoOperationEnum operacao) {
		this.operacao = operacao;
	}

	public Integer getPontos() {
		return pontos;
	}

	public void setPontos(Integer pontos) {
		this.pontos = pontos;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public boolean isAplicado() {
		return aplicado;
	}

	public void setAplicado(boolean aplicado) {
		this.aplicado = aplicado;
	}

	public boolean isAnulado() {
		return anulado;
	}

	public void setAnulado(boolean anulado) {
		this.anulado = anulado;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	@Override
	public int hashCode() {
		return Objects.hash(contador, turma);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pontuacao other = (Pontuacao) obj;
		return Objects.equals(contador, other.contador) && Objects.equals(turma, other.turma);
	}
	
	public static Builder builder() {
		return new Builder();
	}	

	public static final class Builder {
		Integer contador;
		Turma turma;
		PontuacaoOperationEnum operacao;
		Integer pontos;
		String descricao;
		boolean aplicado;
		boolean anulado;
		Date data;

		public Builder contador(Integer contador) {
			this.contador = contador;
			return this;
		}

		public Builder turma(Turma turma) {
			this.turma = turma;
			return this;
		}

		public Builder operacao(PontuacaoOperationEnum operacao) {
			this.operacao = operacao;
			return this;
		}

		public Builder pontos(Integer pontos) {
			this.pontos = pontos;
			return this;
		}

		public Builder descricao(String descricao) {
			this.descricao = descricao;
			return this;
		}

		public Builder aplicado(boolean aplicado) {
			this.aplicado = aplicado;
			return this;
		}

		public Builder anulado(boolean anulado) {
			this.anulado = anulado;
			return this;
		}

		public Builder data(Date data) {
			this.data = data;
			return this;
		}

		public Pontuacao build() {
			return new Pontuacao(this);
		}
	}

}
