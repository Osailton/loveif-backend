package com.amorif.entities;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
	
	@OneToMany(mappedBy = "tipoRegra", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Regra> regras;
	
}
