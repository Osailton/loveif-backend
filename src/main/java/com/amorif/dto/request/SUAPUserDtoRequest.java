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

@Getter
@Setter
@Builder
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

	public SUAPUserDtoRequest(String matricula, String nomeUsual, String tipoVinculo, String email) {
		super();
		this.matricula = matricula;
		this.nomeUsual = nomeUsual;
		this.tipoVinculo = tipoVinculo;
		this.email = email;
	}

}
