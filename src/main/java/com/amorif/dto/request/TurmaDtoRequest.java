package com.amorif.dto.request;

import java.io.Serializable;
import java.util.Objects;

public class TurmaDtoRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long idAnoLetivo;

	private String nome;

	private String descricao;

	public TurmaDtoRequest() {

	}

	public Long getIdAnoLetivo() {
		return idAnoLetivo;
	}

	public void setIdAnoLetivo(Long idAnoLetivo) {
		this.idAnoLetivo = idAnoLetivo;
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

	@Override
	public int hashCode() {
		return Objects.hash(idAnoLetivo, nome);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TurmaDtoRequest other = (TurmaDtoRequest) obj;
		return Objects.equals(idAnoLetivo, other.idAnoLetivo) && Objects.equals(nome, other.nome);
	}

}
