package com.amorif.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.amorif.entities.Pontuacao;
import com.amorif.entities.Turma;
import com.amorif.entities.User;

/**
 * @author osailton
 */

public interface PontuacaoRepository extends JpaRepository<Pontuacao, Integer> {

	@Query("SELECT COUNT(p) > 0 FROM Pontuacao p WHERE p.anoLetivo.id = :anoLetivoId AND p.regra.id = :regraId AND p.turma.id = :turmaId AND p.anulado = false")
	boolean existsByYearAndRule(@Param("anoLetivoId") Long anoLetivoId, @Param("regraId") Long regraId,
			@Param("turmaId") Long turmaId);

	@Query("SELECT COUNT(p) > 0 FROM Pontuacao p WHERE p.bimestre = :bimestre AND p.regra.id = :regraId AND p.turma.id = :turmaId AND p.anulado = false")
	boolean existsByBimesterAndRule(@Param("bimestre") Integer bimestre, @Param("regraId") Long regraId,
			@Param("turmaId") Long turmaId);
	
	@Query("SELECT COUNT(p) > 0 FROM Pontuacao p WHERE p.anoLetivo.id = :anoLetivoId AND p.turma.id = :turmaId AND p.anulado = false AND p.regra.grupo = :grupo")
	boolean existsByYearAndGroup(@Param("anoLetivoId") Long anoLetivoId,
			@Param("turmaId") Long turmaId, @Param("grupo") String grupo);

	@Query("SELECT COUNT(p) > 0 FROM Pontuacao p WHERE p.bimestre = :bimestre AND p.turma.id = :turmaId AND p.anulado = false AND p.regra.grupo = :grupo")
	boolean existsByBimesterAndGroup(@Param("bimestre") Integer bimestre,
			@Param("turmaId") Long turmaId, @Param("grupo") String grupo);

	@Query("SELECT COUNT(p) > 0 FROM Pontuacao p WHERE p.anoLetivo.id = :anoLetivoId AND p.regra.id = :regraId AND p.turma.id = :turmaId AND matriculaAluno = :matriculaAluno AND p.anulado = false")
	boolean existsByYearAndRulePerStudent(@Param("anoLetivoId") Long anoLetivoId, @Param("regraId") Long regraId,
			@Param("turmaId") Long turmaId, @Param("matriculaAluno") String matriculaAluno);

	@Query("SELECT COUNT(p) > 0 FROM Pontuacao p WHERE p.bimestre = :bimestre AND p.regra.id = :regraId AND p.turma.id = :turmaId AND matriculaAluno = :matriculaAluno AND p.anulado = false")
	boolean existsByBimesterAndRulePerStudent(@Param("bimestre") Integer bimestre, @Param("regraId") Long regraId,
			@Param("turmaId") Long turmaId, @Param("matriculaAluno") String matriculaAluno);
	
	@Query("SELECT COUNT(p) > 0 FROM Pontuacao p WHERE p.anoLetivo.id = :anoLetivoId AND p.turma.id = :turmaId AND matriculaAluno = :matriculaAluno AND p.anulado = false AND p.regra.grupo = :grupo")
	boolean existsByYearAndGroupPerStudent(@Param("anoLetivoId") Long anoLetivoId,
			@Param("turmaId") Long turmaId, @Param("matriculaAluno") String matriculaAluno, @Param("grupo") String grupo);

	@Query("SELECT COUNT(p) > 0 FROM Pontuacao p WHERE p.bimestre = :bimestre AND p.turma.id = :turmaId AND matriculaAluno = :matriculaAluno AND p.anulado = false AND p.regra.grupo = :grupo")
	boolean existsByBimesterAndGroupPerStudent(@Param("bimestre") Integer bimestre,
			@Param("turmaId") Long turmaId, @Param("matriculaAluno") String matriculaAluno, @Param("grupo") String grupo);

	@Query(value = "SELECT COALESCE(SUM(p.pontos), 0) FROM Pontuacao p " + "JOIN p.regra r " + "WHERE p.turma = :turma "
			+ "AND r.operacao = 'SUM' " + "AND p.aplicado = true " + "AND p.anulado = false ")
	Integer positivePoints(Turma turma);

	@Query(value = "SELECT COALESCE(SUM(p.pontos), 0) FROM Pontuacao p " + "JOIN p.regra r " + "WHERE p.turma = :turma "
			+ "AND r.operacao = 'SUB' " + "AND p.aplicado = true " + "AND p.anulado = false ")
	Integer negativePoints(Turma turma);

	@Query(value = "SELECT p FROM Pontuacao p " + "WHERE p.turma.anoLetivo.id = :idAno " + "ORDER by p.contador")
	List<Pontuacao> pontosByAno(Long idAno);

	@Query(value = "SELECT p.contador FROM Pontuacao p " + "WHERE p.turma = :turma " + "ORDER BY p.contador DESC "
			+ "LIMIT 1")
	Integer contadorByTurma(Turma turma);

	@Query(value = "SELECT p FROM Pontuacao p " + "WHERE p.contador = :contador " + "AND p.turma = :turma")
	Pontuacao getByContadorTurma(Integer contador, Turma turma);
	
    @Query("SELECT p FROM Pontuacao p WHERE p.user = :user ORDER BY p.data DESC")
    List<Pontuacao> findByUser(@Param("user") User user);
	
	Pontuacao findByContadorAndTurma_Id(Integer contador, Integer turmaId);

    void deleteByContadorAndTurma_Id(Integer contador, Integer turmaId);
}
