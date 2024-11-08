package com.amorif.dto.response;

import java.io.Serializable;

import com.amorif.entities.AnoLetivo;
import com.amorif.entities.Regra;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author osailton
 */
@Builder
@Getter
@Setter
public class PontuacaoDtoResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer contador;
	private TurmaDtoResponse turma;
	private String nomeTurma;
	private Long idTurma;
	private Regra regra;
	private String operacao;
	private int bimestre;
	private AnoLetivo anoLetivo;
	private Integer pontos;
	private String descricao;
	private boolean aplicado;
	private boolean anulado;
	
	public PontuacaoDtoResponse() {
		
	}

	public PontuacaoDtoResponse(Integer contador, TurmaDtoResponse turma, String nomeTurma, Long idTurma, Regra regra,
			String operacao, int bimestre, AnoLetivo anoLetivo, Integer pontos, String descricao, boolean aplicado, boolean anulado) {
		super();
		this.contador = contador;
		this.nomeTurma = nomeTurma;
		this.idTurma = idTurma;
		
		if (turma != null) {
			this.turma = turma;			
		} else {
			this.turma = TurmaDtoResponse.builder().nome(this.nomeTurma).id(this.idTurma).build();
		}
		
		this.regra = regra;
		this.operacao = operacao;
		this.bimestre = bimestre;
		this.anoLetivo = anoLetivo;
		this.pontos = pontos;
		this.descricao = descricao;
		this.aplicado = aplicado;
		this.anulado = anulado;
	}
	
}
