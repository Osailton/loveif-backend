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
	
	@JsonProperty("vinculo")
	private SUAPUserVinculoDtoRequest vinculo;
	
	private String campus;
	
	@JsonProperty("url_foto_75x100")
	private String fotoPerfil;
	
	private String email;
	
	public SUAPUserDtoRequest() {
		
	}

	public SUAPUserDtoRequest(String matricula, String nomeUsual, String tipoVinculo, SUAPUserVinculoDtoRequest vinculo, String campus,
			String fotoPerfil, String email) {
		super();
		this.matricula = matricula;
		this.nomeUsual = nomeUsual;
		this.tipoVinculo = tipoVinculo;
		this.vinculo = vinculo;
		this.campus = campus;
		this.fotoPerfil = fotoPerfil;
		this.email = email;
	}

}

