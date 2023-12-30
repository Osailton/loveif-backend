package com.amorif.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.amorif.entities.AnoLetivo;
import com.amorif.entities.Turma;

/**
 * @author osailton 
 */
public interface TurmaRepository extends JpaRepository<Turma, Long> {
	
	List<Turma> findAllByAnoLetivo(AnoLetivo anoLetivo);

}
