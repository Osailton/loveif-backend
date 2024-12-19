package com.amorif.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import com.amorif.entities.AnoLetivo;
import com.amorif.entities.Turma;

import jakarta.persistence.QueryHint;

/**
 * @author osailton 
 */
public interface TurmaRepository extends JpaRepository<Turma, Long> {
	
	List<Turma> findAllByAnoLetivo(AnoLetivo anoLetivo);
	
	List<Turma> findAllByTurno(Integer turno);
	
	@Query("SELECT t FROM Turma t JOIN Pontuacao p ON p.turma.id = t.id WHERE p.regra.id = :regraId")
	List<Turma> findTurmasQualificadasParaBonus(@Param("regraId") Long regraId);

}
