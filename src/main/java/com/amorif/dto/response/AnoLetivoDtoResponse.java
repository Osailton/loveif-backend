package com.amorif.dto.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author osailton
 */
public class AnoLetivoDtoResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	@JsonProperty("ano_letivo")
	private Integer anoLetivo;
	
	private String status;
	
	public AnoLetivoDtoResponse() {

	}
	
	public AnoLetivoDtoResponse(Builder builder) {
		this.id = builder.id;
		this.anoLetivo = builder.anoLetivo;
		this.status = builder.status;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getAnoLetivo() {
		return anoLetivo;
	}

	public void setAnoLetivo(Integer anoLetivo) {
		this.anoLetivo = anoLetivo;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static final class Builder {
		Long id;
		Integer anoLetivo;
		String status;
		
		public Builder id(Long id) {
			this.id = id;
			return this;
		}
		
		public Builder anoLetivo(Integer anoLetivo) {
			this.anoLetivo = anoLetivo;
			return this;
		}
		
		public Builder status(String status) {
			this.status = status;
			return this;
		}
		
		public AnoLetivoDtoResponse build() {
			return new AnoLetivoDtoResponse(this);
		}
	}

}
