package com.amorif.entities;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@Entity
@Table(name = "regra")
public class Regra implements Serializable {
private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true)
	private String descricao;
	
	@Column(unique = true)
	private int valorMinimo;
	
	@Column(unique = false)
	private int valorMaximo;
	
	@ManyToOne
	@JoinColumn(name = "id")
	private Senso senso;
	
	@ManyToOne
	@JoinColumn(name = "id")
	private TipoRegra tipoRegra;

}
