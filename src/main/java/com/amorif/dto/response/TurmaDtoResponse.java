package com.amorif.dto.response;

import java.io.Serializable;

/**
 * @author osailton
 */

public class TurmaDtoResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer anoLetivo;
	private String nome;
	private String descricao;
	private Integer pontuacao;

	public TurmaDtoResponse() {

	}

	public TurmaDtoResponse(Builder builder) {
		this.anoLetivo = builder.anoLetivo;
		this.nome = builder.nome;
		this.descricao = builder.descricao;
		this.pontuacao = builder.pontuacao;
	}

	public Integer getAnoLetivo() {
		return anoLetivo;
	}

	public void setAnoLetivo(Integer anoLetivo) {
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

	public Integer getPontuacao() {
		return pontuacao;
	}

	public void setPontuacao(Integer pontuacao) {
		this.pontuacao = pontuacao;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static final class Builder {
		Integer anoLetivo;
		String nome;
		String descricao;
		Integer pontuacao;

		public Builder anoLetivo(Integer anoLetivo) {
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

		public Builder pontuacao(Integer pontuacao) {
			this.pontuacao = pontuacao;
			return this;
		}
		
		public TurmaDtoResponse build() {
			return new TurmaDtoResponse(this);
		}
	}

}
