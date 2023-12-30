package com.amorif.entities;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;

/**
 * @author osailton
 * 
 *         Composite id for Pontuacao
 */

@Embeddable
public class PontuacaoID implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer contador;

	private Turma turma;

	public PontuacaoID() {

	}

	public PontuacaoID(Integer contador, Turma turma) {
		this.contador = contador;
		this.turma = turma;
	}

	public Integer getContador() {
		return contador;
	}

	public void setContador(Integer contador) {
		this.contador = contador;
	}

	public Turma getTurma() {
		return turma;
	}

	public void setTurma(Turma turma) {
		this.turma = turma;
	}

	@Override
	public int hashCode() {
		return Objects.hash(contador, turma);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PontuacaoID other = (PontuacaoID) obj;
		return Objects.equals(contador, other.contador) && Objects.equals(turma, other.turma);
	}
}
