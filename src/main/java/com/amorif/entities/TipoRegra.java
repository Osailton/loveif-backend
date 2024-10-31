package com.amorif.entities;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@Entity
@Table(name = "tipo_regra")
public class TipoRegra implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private boolean fixo;
	
	@Column(nullable = false)
	private String operacao;

	@Column(nullable = false)
	private boolean temAluno;
	
	@Column(nullable = false)
	private int frequencia;
	
	public TipoRegra() {
		
	}

	public TipoRegra(Long id, boolean fixo, String operacao, boolean temAluno, int frequencia) {
		super();
		this.id = id;
		this.fixo = fixo;
		this.operacao = operacao;
		this.temAluno = temAluno;
		this.frequencia = frequencia;
	}
	
}
