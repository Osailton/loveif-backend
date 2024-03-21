package com.amorif.dto.request;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author osailton
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class PontuacaoDtoRequest implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@JsonProperty("id_turma")
	private Long idTurma;
	
	@JsonProperty("pontos")
	private Integer pontos;
	
	@JsonProperty("operacao")
	private String operacao;
	
	@JsonProperty("descricao")
	private String descricao;
	
	public PontuacaoDtoRequest() {

	}

	public Long getIdTurma() {
		return idTurma;
	}

	public void setIdTurma(Long idTurma) {
		this.idTurma = idTurma;
	}

	public Integer getPontos() {
		return pontos;
	}

	public void setPontos(Integer pontos) {
		this.pontos = pontos;
	}

	public String getOperacao() {
		return operacao;
	}

	public void setOperacao(String operacao) {
		this.operacao = operacao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}
