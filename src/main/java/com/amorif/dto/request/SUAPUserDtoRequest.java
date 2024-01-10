package com.amorif.dto.request;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author osailton
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class SUAPUserDtoRequest implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String matricula;
	
	@JsonProperty("nome_usual")
	private String nomeUsual;
	
	@JsonProperty("tipo_vinculo")
	private String tipoVinculo;
	
	private String email;
	
	public SUAPUserDtoRequest() {
		
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getNomeUsual() {
		return nomeUsual;
	}

	public void setNomeUsual(String nomeUsual) {
		this.nomeUsual = nomeUsual;
	}

	public String getTipoVinculo() {
		return tipoVinculo;
	}

	public void setTipoVinculo(String tipoVinculo) {
		this.tipoVinculo = tipoVinculo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
