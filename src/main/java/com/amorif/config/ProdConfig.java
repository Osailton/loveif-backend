package com.amorif.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.amorif.entities.FrequenciaRegraEnum;
import com.amorif.entities.Role;
import com.amorif.entities.RoleEnum;
import com.amorif.entities.Senso;
import com.amorif.entities.Regra;
import com.amorif.entities.TipoRegra;
import com.amorif.repository.RoleRepository;
import com.amorif.repository.SensoRepository;
import com.amorif.repository.TipoRegraRepository;
import com.amorif.repository.RegraRepository;

/**
 * @author manoel
 */

@Configuration
@Profile("prod")
public class ProdConfig implements CommandLineRunner {

	private final RoleRepository roleRepository;
	private final SensoRepository sensoRepository;
	private final TipoRegraRepository tipoRegraRepository;
	private final RegraRepository regraRepository;

	public ProdConfig(RoleRepository roleRepository, SensoRepository sensoRepository,
			TipoRegraRepository tipoRegraRepository, RegraRepository regraRepository) {
		this.roleRepository = roleRepository;
		this.sensoRepository = sensoRepository;
		this.tipoRegraRepository = tipoRegraRepository;
		this.regraRepository = regraRepository;
	}

	@Override
	public void run(String... args) throws Exception {
//		Create Roles
		if (roleRepository.count() == 0) {
			Role r1 = Role.builder().name(RoleEnum.ROLE_ADMINISTRADOR.toString()).build();
			Role r2 = Role.builder().name(RoleEnum.ROLE_AVAL.toString()).build();
			Role r4 = Role.builder().name(RoleEnum.ROLE_ALUNO.toString()).build();
			Role r5 = Role.builder().name(RoleEnum.ROLE_BIBLIOTECARIO.toString()).build();
			Role r6 = Role.builder().name(RoleEnum.ROLE_APOIO_ACADEMICO.toString()).build();
			Role r7 = Role.builder().name(RoleEnum.ROLE_SISTEMA.toString()).build();
			Role r8 = Role.builder().name(RoleEnum.ROLE_DOCENTE.toString()).build();
			Role r9 = Role.builder().name(RoleEnum.ROLE_ASSESSORIA_PEDAGOGICA.toString()).build();
			Role r10 = Role.builder().name(RoleEnum.ROLE_COEXPEIN.toString()).build();
			Role r11 = Role.builder().name(RoleEnum.ROLE_COORDENADOR_CURSO.toString()).build();
			Role r12 = Role.builder().name(RoleEnum.ROLE_ASSESSORIA_LABORATORIO.toString()).build();
			Role r13 = Role.builder().name(RoleEnum.ROLE_ASSISTENCIA_ESTUDANTIL.toString()).build();
			Role r14 = Role.builder().name(RoleEnum.ROLE_SEAC.toString()).build();
			roleRepository.saveAll(Arrays.asList(r1, r2, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14));
		}

		// Criar sensos
		if (sensoRepository.count() == 0) {
			Senso senso1 = Senso.builder().descricao("Utilização").build();
			Senso senso2 = Senso.builder().descricao("Ordenação").build();
			Senso senso3 = Senso.builder().descricao("Limpeza").build();
			Senso senso4 = Senso.builder().descricao("Saúde").build();
			Senso senso5 = Senso.builder().descricao("Autodisciplina").build();

			sensoRepository.saveAll(Arrays.asList(senso1, senso2, senso3, senso4, senso5));
		}

		// Criar tipos de regra
		if (tipoRegraRepository.count() == 0) {
			TipoRegra tipo1 = TipoRegra.builder().descricao("Valor Fixo").fixo(true).temAluno(false)
					.frequencia(FrequenciaRegraEnum.AVULSO.ordinal()).build();
			TipoRegra tipo2 = TipoRegra.builder().descricao("Valor Fixo em bimestre extra por ano letivo").fixo(true)
					.temAluno(false).frequencia(FrequenciaRegraEnum.ANUAL.ordinal()).bimestreExtra(true).build();
			TipoRegra tipo3 = TipoRegra.builder().descricao("Valor Fixo automático por bimestre").fixo(true)
					.temAluno(false).frequencia(FrequenciaRegraEnum.BIMESTRAL.ordinal()).automatico(true).build();
			TipoRegra tipo4 = TipoRegra.builder().descricao("Valor Fixo por turno").fixo(true).temAluno(false)
					.frequencia(FrequenciaRegraEnum.AVULSO.ordinal()).porTurno(true).build();
			TipoRegra tipo5 = TipoRegra.builder().descricao("Valor Fixo por aluno por bimestre").fixo(true)
					.temAluno(true).frequencia(FrequenciaRegraEnum.BIMESTRAL.ordinal()).build();
			TipoRegra tipo6 = TipoRegra.builder().descricao("Valor Variável por bimestre").fixo(false).temAluno(false)
					.frequencia(FrequenciaRegraEnum.BIMESTRAL.ordinal()).build();
			TipoRegra tipo7 = TipoRegra.builder().descricao("Valor Fixo por aluno por ano letivo").fixo(true)
					.temAluno(true).frequencia(FrequenciaRegraEnum.ANUAL.ordinal()).build();
			TipoRegra tipo8 = TipoRegra.builder().descricao("Valor Variável").fixo(false).temAluno(false)
					.frequencia(FrequenciaRegraEnum.AVULSO.ordinal()).build();
			TipoRegra tipo9 = TipoRegra.builder().descricao("Valor Fixo por bimestre").fixo(true).temAluno(false)
					.frequencia(FrequenciaRegraEnum.BIMESTRAL.ordinal()).build();

			tipoRegraRepository.saveAll(Arrays.asList(tipo1, tipo2, tipo3, tipo4, tipo5, tipo6, tipo7, tipo8, tipo9));
		}

		// População de regras
		if (regraRepository.count() == 0) {
			// Obter instâncias de Senso
			Senso utilizacao = sensoRepository.findByDescricao("Utilização");
			Senso ordenacao = sensoRepository.findByDescricao("Ordenação");
			Senso limpeza = sensoRepository.findByDescricao("Limpeza");
			Senso saude = sensoRepository.findByDescricao("Saúde");
			Senso autodisciplina = sensoRepository.findByDescricao("Autodisciplina");

			// Obter instâncias de TipoRegra
			TipoRegra tipoFixo = tipoRegraRepository.findByDescricao("Valor Fixo");
			TipoRegra tipoFixoBimestreExtra = tipoRegraRepository
					.findByDescricao("Valor Fixo em bimestre extra por ano letivo");
			TipoRegra tipoAutomatico = tipoRegraRepository.findByDescricao("Valor Fixo automático por bimestre");
			TipoRegra tipoPorTurno = tipoRegraRepository.findByDescricao("Valor Fixo por turno");
			TipoRegra tipoPorAlunoBimestre = tipoRegraRepository.findByDescricao("Valor Fixo por aluno por bimestre");
			TipoRegra tipoVariavelBimestre = tipoRegraRepository.findByDescricao("Valor Variável por bimestre");
			TipoRegra tipoPorAlunoAno = tipoRegraRepository.findByDescricao("Valor Fixo por aluno por ano letivo");
			TipoRegra tipoVariavel = tipoRegraRepository.findByDescricao("Valor Variável");
			TipoRegra tipoFixoPorBimestre = tipoRegraRepository.findByDescricao("Valor Fixo por bimestre");

			// Obter instâncias de Role
			Role bibliotecario = roleRepository.getByName("ROLE_BIBLIOTECARIO");
			Role apoioAcademico = roleRepository.getByName("ROLE_APOIO_ACADEMICO");
			Role sistema = roleRepository.getByName("ROLE_SISTEMA");
			Role docente = roleRepository.getByName("ROLE_DOCENTE");
			Role assessoriaPedagogica = roleRepository.getByName("ROLE_ASSESSORIA_PEDAGOGICA");
			Role coexpein = roleRepository.getByName("ROLE_COEXPEIN");
			Role coordenadorCurso = roleRepository.getByName("ROLE_COORDENADOR_CURSO");
			Role assistenciaEstudantil = roleRepository.getByName("ROLE_ASSISTENCIA_ESTUDANTIL");
			Role assessoriaLaboratorio = roleRepository.getByName("ROLE_ASSESSORIA_LABORATORIO");
			Role administrador = roleRepository.getByName("ROLE_ADMINISTRADOR");
			Role seac = roleRepository.getByName("ROLE_SEAC");

			// População das regras
			List<Regra> regras = Arrays.asList(
				// Utilização - Bibliotecário - Positivas
				Regra.builder().descricao("1 ponto por livro emprestado").operacao("SUM").valorMinimo(1)
						.senso(utilizacao).tipoRegra(tipoFixo).roles(Arrays.asList(bibliotecario, administrador))
						.build(),
				Regra.builder().descricao("30 pontos por campanha de doação").operacao("SUM").valorMinimo(30)
						.senso(utilizacao).tipoRegra(tipoFixo).roles(Arrays.asList(bibliotecario, administrador))
						.build(),
				Regra.builder().descricao(
						"20 pontos no bimestre extra para a turma que mais tiver formado grupos de estudo no ano letivo")
						.operacao("SUM").valorMinimo(20).senso(utilizacao).tipoRegra(tipoFixoBimestreExtra)
						.roles(Arrays.asList(bibliotecario, administrador)).build(),

				// Utilização - Bibliotecário - Negativas
				Regra.builder().descricao("20 pontos por perda de livro").operacao("SUB").valorMinimo(20)
						.senso(utilizacao).tipoRegra(tipoFixo).roles(Arrays.asList(bibliotecario, administrador))
						.build(),
				Regra.builder().descricao("3 pontos por atraso de livro").operacao("SUB").valorMinimo(3)
						.senso(utilizacao).tipoRegra(tipoFixo).roles(Arrays.asList(bibliotecario, administrador))
						.build(),
				Regra.builder().descricao("5 pontos por avaria de livro").operacao("SUB").valorMinimo(5)
						.senso(utilizacao).tipoRegra(tipoFixo).roles(Arrays.asList(bibliotecario, administrador))
						.build(),
				Regra.builder().descricao("Pontos por trapaça").operacao("SUB").valorMinimo(1).senso(utilizacao)
						.tipoRegra(tipoVariavel).roles(Arrays.asList(bibliotecario, administrador)).build(),

				// Ordenação - Apoio Acadêmico - Positivas
				Regra.builder().descricao("0 pontos pela organização ruim").operacao("SUM").valorMinimo(0)
						.senso(ordenacao).tipoRegra(tipoFixo).roles(Arrays.asList(apoioAcademico, administrador))
						.build(),
				Regra.builder().descricao("5 pontos pela organização mediana").operacao("SUM").valorMinimo(5)
						.senso(ordenacao).tipoRegra(tipoFixo).roles(Arrays.asList(apoioAcademico, administrador))
						.build(),
				Regra.builder().descricao("8 pontos pela organização boa").operacao("SUM").valorMinimo(8)
						.senso(ordenacao).tipoRegra(tipoFixo).roles(Arrays.asList(apoioAcademico, administrador))
						.build(),
				Regra.builder().descricao("10 pontos pela organização excelente").operacao("SUM").valorMinimo(10)
						.senso(ordenacao).tipoRegra(tipoFixo).roles(Arrays.asList(apoioAcademico, administrador))
						.build(),

				// Ordenação - Sistema - Positivas
				Regra.builder().descricao("20 pontos ao fim de cada bimestre se a turma tiver 100% de nota 10")
						.operacao("SUM").valorMinimo(20).senso(ordenacao).tipoRegra(tipoAutomatico)
						.roles(Arrays.asList(sistema, administrador)).build(),

				// Ordenação - Assistência Estudantil, Apoio Acadêmico e ASLAB - Negativas
				Regra.builder().descricao("10 pontos por desordem para todas as turmas do turno").operacao("SUB")
						.valorMinimo(10).senso(ordenacao).tipoRegra(tipoPorTurno)
						.roles(Arrays.asList(assistenciaEstudantil, apoioAcademico, assessoriaLaboratorio,
								administrador))
						.build(),

				// Limpeza - Apoio Acadêmico - Positivas
				Regra.builder().descricao("0 pontos pela limpeza ruim").operacao("SUM").valorMinimo(0).senso(limpeza)
						.tipoRegra(tipoFixo).roles(Arrays.asList(apoioAcademico, administrador)).build(),
				Regra.builder().descricao("5 pontos pela limpeza mediana").operacao("SUM").valorMinimo(5).senso(limpeza)
						.tipoRegra(tipoFixo).roles(Arrays.asList(apoioAcademico, administrador)).build(),
				Regra.builder().descricao("8 pontos pela limpeza boa").operacao("SUM").valorMinimo(8).senso(limpeza)
						.tipoRegra(tipoFixo).roles(Arrays.asList(apoioAcademico, administrador)).build(),
				Regra.builder().descricao("10 pontos pela limpeza excelente").operacao("SUM").valorMinimo(10)
						.senso(limpeza).tipoRegra(tipoFixo).roles(Arrays.asList(apoioAcademico, administrador)).build(),

				// Limpeza - Apoio Acadêmico - Negativas
				Regra.builder().descricao("10 pontos por falta de limpeza para todas as turmas do turno")
						.operacao("SUB").valorMinimo(10).senso(limpeza).tipoRegra(tipoPorTurno)
						.roles(Arrays.asList(apoioAcademico, administrador)).build(),

				// Limpeza - Sistema - Positivas
				Regra.builder().descricao("20 pontos ao fim de cada bimestre se a turma tiver 100% de nota 10")
						.operacao("SUM").valorMinimo(20).senso(limpeza).tipoRegra(tipoAutomatico)
						.roles(Arrays.asList(sistema, administrador)).build(),

				// Limpeza - Administrador - Positivas
				Regra.builder().descricao("Até 20 pontos por campanha de coleta de lixo no campus").operacao("SUM")
						.valorMinimo(1).valorMaximo(20).senso(limpeza).tipoRegra(tipoVariavelBimestre)
						.roles(Arrays.asList(administrador)).build(),

				// Saúde - Docente - Positivas
				Regra.builder().descricao("0 pontos pela média menor ao do bimestre anterior").operacao("SUM")
						.grupo("media_comparativa").valorMinimo(0).senso(saude).tipoRegra(tipoFixoPorBimestre)
						.roles(Arrays.asList(docente, administrador)).build(),
				Regra.builder().descricao("8 pontos pela média igual ao do bimestre anterior").operacao("SUM")
						.grupo("media_comparativa").valorMinimo(8).senso(saude).tipoRegra(tipoFixoPorBimestre)
						.roles(Arrays.asList(docente, administrador)).build(),
				Regra.builder().descricao("20 pontos pela média maior ao do bimestre anterior").operacao("SUM")
						.grupo("media_comparativa").valorMinimo(20).senso(saude).tipoRegra(tipoFixoPorBimestre)
						.roles(Arrays.asList(docente, administrador)).build(),

				Regra.builder().descricao("0 pontos pela frequência menor ao do bimestre anterior").operacao("SUM")
						.grupo("frequencia_comparativa").valorMinimo(0).senso(saude).tipoRegra(tipoFixoPorBimestre)
						.roles(Arrays.asList(docente, administrador)).build(),
				Regra.builder().descricao("8 pontos pela frequência igual ao do bimestre anterior").operacao("SUM")
						.grupo("frequencia_comparativa").valorMinimo(8).senso(saude).tipoRegra(tipoFixoPorBimestre)
						.roles(Arrays.asList(docente, administrador)).build(),
				Regra.builder().descricao("20 pontos pela frequência maior ao do bimestre anterior").operacao("SUM")
						.grupo("frequencia_comparativa").valorMinimo(20).senso(saude).tipoRegra(tipoFixoPorBimestre)
						.roles(Arrays.asList(docente, administrador)).build(),

				Regra.builder().descricao("10 pontos para a turma que mais participou de CAs no bimestre (opcional)")
						.operacao("SUM").valorMinimo(10).senso(saude).tipoRegra(tipoFixoPorBimestre)
						.roles(Arrays.asList(docente, administrador)).build(),

				// Saúde - Docente - Negativas
				Regra.builder().descricao("0 a 15 pontos por bimestre por mau comportamento").operacao("SUB")
						.valorMinimo(0).valorMaximo(15).senso(saude).tipoRegra(tipoVariavelBimestre)
						.roles(Arrays.asList(docente, administrador)).build(),

				// Saúde - Coordenação de Curso - Positivas

				Regra.builder().descricao(
						"2 pontos por aluno de cada turma que participar de olimpíadas coordenadas pelo professor no bimestre extra")
						.operacao("SUM").valorMinimo(2).senso(saude).tipoRegra(tipoPorAlunoAno)
						.roles(Arrays.asList(coordenadorCurso, administrador)).build(),

				Regra.builder().descricao("1 ponto por aluno da turma em cada bimestre por atuação em monitoria")
						.operacao("SUM").valorMinimo(1).senso(saude).tipoRegra(tipoPorAlunoBimestre)
						.roles(Arrays.asList(coordenadorCurso, administrador)).build(),

				// Saúde - Assessoria Pedagógica - Positivas
				Regra.builder().descricao("1 ponto para a turma por aluno pela elaboração de plano de estudos")
						.operacao("SUM").valorMinimo(1).senso(saude).tipoRegra(tipoPorAlunoAno)
						.roles(Arrays.asList(assessoriaPedagogica, administrador)).build(),

				Regra.builder().descricao(
						"40 pontos para a turma com presença dos pais maior que 75% nas reuniões de pais por reunião")
						.operacao("SUM").valorMinimo(40).senso(saude).tipoRegra(tipoFixo)
						.roles(Arrays.asList(assessoriaPedagogica, administrador)).build(),

				// Saúde - Assessoria Pedagógica - Positivas
				Regra.builder().descricao(
						"1 ponto por aluno da turma em cada bimestre por atuação em projetos de pesquisa/extensão")
						.operacao("SUM").valorMinimo(1).senso(saude).tipoRegra(tipoPorAlunoBimestre)
						.roles(Arrays.asList(coexpein, administrador)).build(),

				Regra.builder().descricao("5 pontos por premiação de aluno da turma na Expotec do campus")
						.operacao("SUM").valorMinimo(5).senso(saude).tipoRegra(tipoFixo)
						.roles(Arrays.asList(coexpein, administrador)).build(),

				Regra.builder()
						.descricao(
								"2 pontos por participação do aluno da turma em eventos científicos externos ao campus")
						.operacao("SUM").valorMinimo(2).senso(saude).tipoRegra(tipoFixo)
						.roles(Arrays.asList(coexpein, administrador)).build(),

				// Autodisciplina - Apoio Acadêmico - Positivas
				Regra.builder().descricao("2 pontos por delação premiada").operacao("SUM").valorMinimo(2)
						.senso(autodisciplina).tipoRegra(tipoFixo)
						.roles(Arrays.asList(apoioAcademico, seac, administrador)).build(),

				// Autodisciplina - Apoio Acadêmico - Negativas
				Regra.builder().descricao("1 ponto por aluno da turma notificado").operacao("SUB").valorMinimo(1)
						.senso(autodisciplina).tipoRegra(tipoFixo)
						.roles(Arrays.asList(apoioAcademico, seac, administrador)).build(),
				Regra.builder().descricao("Pontos por turma notificada").operacao("SUB").senso(autodisciplina)
						.tipoRegra(tipoVariavel).roles(Arrays.asList(apoioAcademico, seac, administrador)).build(),
				Regra.builder().descricao("5 pontos por dia por aluno da turma suspenso").operacao("SUB").valorMinimo(1)
						.senso(autodisciplina).tipoRegra(tipoVariavel)
						.roles(Arrays.asList(apoioAcademico, seac, administrador)).build(),

				// Autodisciplina - Coordenador de Curso e Assessoria Pedagógica - Negativas
				Regra.builder().descricao("0 a 15 pontos por bimestre por campanha de conscientização realizada")
						.operacao("SUM").valorMinimo(0).valorMaximo(15).senso(autodisciplina)
						.tipoRegra(tipoVariavelBimestre)
						.roles(Arrays.asList(coordenadorCurso, assessoriaPedagogica, administrador)).build(),

				// Autodisciplina - Administrador - Negativas
				Regra.builder().descricao("5 a 10 pontos por má conduta em eventos").operacao("SUB").valorMinimo(5)
						.valorMaximo(10).senso(autodisciplina).tipoRegra(tipoVariavel)
						.roles(Arrays.asList(administrador)).build());

			regraRepository.saveAll(regras);
		}
	}

}
