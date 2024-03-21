package com.amorif.dto.response;

import java.io.Serializable;

/**
 * @author osailton
 */
public class PontuacaoDtoResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer contador;
	private TurmaDtoResponse turma;
	private String operacao;
	private Integer pontos;
	private String descricao;
	private boolean aplicado;
	private boolean anulado;
	
	public PontuacaoDtoResponse() {
		
	}
	
	public PontuacaoDtoResponse(Builder builder) {
		this.contador = builder.contador;
		this.turma = TurmaDtoResponse.builder().nome(builder.nomeTurma).id(builder.idTurma).build();
		this.operacao = builder.operacao;
		this.pontos = builder.pontos;
		this.descricao = builder.descricao;
		this.aplicado = builder.aplicado;
		this.anulado = builder.anulado;
	}

	public Integer getContador() {
		return contador;
	}

	public void setContador(Integer contador) {
		this.contador = contador;
	}

	public TurmaDtoResponse getTurma() {
		return turma;
	}

	public void setTurma(TurmaDtoResponse turma) {
		this.turma = turma;
	}

	public String getOperacao() {
		return operacao;
	}

	public void setOperacao(String operacao) {
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

	public static Builder builder() {
		return new Builder();
	}
	
	public static final class Builder {
		Integer contador;
		String nomeTurma;
		Long idTurma;
		String operacao;
		Integer pontos;
		String descricao;
		boolean aplicado;
		boolean anulado;
		
		public Builder contador(Integer contador) {
			this.contador = contador;
			return this;
		}
		
		public Builder nomeTurma(String nomeTurma) {
			this.nomeTurma = nomeTurma;
			return this;
		}
		
		public Builder idTurma(Long idTurma) {
			this.idTurma = idTurma;
			return this;
		}
		
		public Builder operacao(String operacao) {
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
		
		public PontuacaoDtoResponse build() {
			return new PontuacaoDtoResponse(this);
		}
	}

}
