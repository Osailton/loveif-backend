package com.amorif.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author osailton
 * 
 *         Represents the collection in wich the students are located It must
 *         have an AnoLetivo as an id value
 */

@Builder
@Getter
@Setter
@Entity
@Table(name = "turma")
public class Turma implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ano_letivo_id")
	private AnoLetivo anoLetivo;

	private String nome;

	private String descricao;
	
	private Integer turno;

	@JsonIgnore
	@OneToMany(mappedBy = "turma")
	@Builder.Default
	private Set<Pontuacao> pontuacao = new HashSet<>();
	
	public Turma() {
		
	}

	public Turma(Long id, AnoLetivo anoLetivo, String nome, String descricao, Integer turno, Set<Pontuacao> pontuacao) {
		super();
		this.id = id;
		this.anoLetivo = anoLetivo;
		this.nome = nome;
		this.descricao = descricao;
		this.turno = turno;
		this.pontuacao = pontuacao;
	}

}
