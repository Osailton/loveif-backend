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
@Table(name = "senso")
public class Senso implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true)
	private String descricao;
	
	@OneToMany(mappedBy = "senso", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Regra> regras;
	
	public Senso() {
		
	}

	public Senso(Long id, String descricao, List<Regra> regras) {
		super();
		this.id = id;
		this.descricao = descricao;
		this.regras = regras;
	}

}
