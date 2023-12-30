package com.amorif.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.amorif.entities.AnoLetivo;

/**
 * @author osailton
 */

public interface AnoLetivoRepository extends JpaRepository<AnoLetivo, Long> {
	
	// Get the last active AnoLetivo
	@Query(value = "SELECT a FROM AnoLetivo a "
			+ "WHERE a.aberto = true "
			+ "ORDER BY a.ano DESC "
			+ "LIMIT 1")
	AnoLetivo getLastActiveAnoLetivo();

}
