package com.amorif.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.amorif.entities.Pontuacao;
import com.amorif.entities.Turma;

/**
 * @author osailton
 */

public interface PontuacaoRepository extends JpaRepository<Pontuacao, Integer> {
	
	@Query(value = "SELECT COALESCE(SUM(p.pontos), 0) FROM Pontuacao p "
			+ "WHERE p.turma = :turma "
			+ "AND p.operacao = 'SUM' "
			+ "AND p.aplicado = true "
			+ "AND p.anulado = false ")
	Integer positivePoints(Turma turma);
	
	@Query(value = "SELECT COALESCE(SUM(p.pontos), 0) FROM Pontuacao p "
			+ "WHERE p.turma = :turma "
			+ "AND p.operacao = 'SUB' "
			+ "AND p.aplicado = true "
			+ "AND p.anulado = false ")
	Integer negativePoints(Turma turma);
	
	@Query(value = "SELECT p FROM Pontuacao p "
			+ "WHERE p.turma.anoLetivo.id = :idAno "
			+ "ORDER by p.contador")
	List<Pontuacao> pontosByAno(Long idAno);
	
	@Query(value = "SELECT p.contador FROM Pontuacao p "
			+ "WHERE p.turma = :turma "
			+ "ORDER BY p.contador DESC "
			+ "LIMIT 1")
	Integer contadorByTurma(Turma turma);

}
