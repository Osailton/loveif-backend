package com.amorif.dto.request;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author osailton
 */

@Builder
@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PontuacaoDtoRequest implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@JsonProperty("contador")
	private Integer contador;
	
	@JsonProperty("id_turma")
	private Long idTurma;
	
	@JsonProperty("id_regra")
	private Long idRegra;
	
	@JsonProperty("pontos")
	private Integer pontos;
	
	@JsonProperty("operacao")
	private String operacao;
	
	@JsonProperty("motivacao")
	private String motivacao;
	
	public PontuacaoDtoRequest() {

	}

	public PontuacaoDtoRequest(Integer contador, Long idTurma, Long idRegra, Integer pontos, String operacao,
			String motivacao) {
		super();
		this.contador = contador;
		this.idTurma = idTurma;
		this.idRegra = idRegra;
		this.pontos = pontos;
		this.operacao = operacao;
		this.motivacao = motivacao;
	}

}
