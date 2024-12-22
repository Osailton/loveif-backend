package com.amorif.dto.request;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class SUAPUserVinculoDtoRequest implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
    @JsonProperty("setor_suap")
    private String setorSuap;
    
    @JsonProperty("setor_siape")
    private String setorSiape;
    
    private String categoria;
    
    private String[] funcao;

	public SUAPUserVinculoDtoRequest(String setorSuap, String setorSiape, String categoria, String[] funcao) {
		super();
		this.setorSuap = setorSuap;
		this.setorSiape = setorSiape;
		this.categoria = categoria;
		this.funcao = funcao;
	}
    
    
	public SUAPUserVinculoDtoRequest() {
		super();
	}
	
}

