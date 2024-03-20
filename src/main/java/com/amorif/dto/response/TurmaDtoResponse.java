package com.amorif.dto.response;

import java.io.Serializable;

/**
 * @author osailton
 */

public class TurmaDtoResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private AnoLetivoDtoResponse anoLetivo;
	private String nome;
	private String descricao;
	private Integer pontuacao;

	public TurmaDtoResponse() {

	}

	public TurmaDtoResponse(Builder builder) {
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

	public AnoLetivoDtoResponse getAnoLetivo() {
		return anoLetivo;
	}

	public void setAnoLetivo(AnoLetivoDtoResponse anoLetivo) {
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
		Long id;
		AnoLetivoDtoResponse anoLetivo;
		String nome;
		String descricao;
		Integer pontuacao;
		
		public Builder id(Long id) {
			this.id = id;
			return this;
		}

		public Builder anoLetivo(AnoLetivoDtoResponse anoLetivo) {
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
