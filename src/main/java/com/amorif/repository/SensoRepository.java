package com.amorif.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.amorif.entities.Senso;

public interface SensoRepository extends JpaRepository<Senso, Long> {
	
	// Método para buscar um Senso pela descrição
    Senso findByDescricao(String descricao);
	
}
