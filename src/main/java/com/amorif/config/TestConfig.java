package com.amorif.config;

import java.util.Arrays;
import java.util.Date;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.amorif.entities.AnoLetivo;
import com.amorif.entities.Pontuacao;
import com.amorif.entities.PontuacaoOperationEnum;
import com.amorif.entities.Role;
import com.amorif.entities.RoleEnum;
import com.amorif.entities.Turma;
import com.amorif.entities.User;
import com.amorif.repository.AnoLetivoRepository;
import com.amorif.repository.PontuacaoRepository;
import com.amorif.repository.RoleRepository;
import com.amorif.repository.TurmaRepository;
import com.amorif.repository.UserRepository;

/**
 * @author osailton
 */

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {

	private final AnoLetivoRepository anoLetivoRepository;
	private final TurmaRepository turmaRepository;
	private final PontuacaoRepository pontuacaoRepository;
	private final RoleRepository roleRepository;
	private final UserRepository userRepository;

	public TestConfig(AnoLetivoRepository anoLetivoRepository, TurmaRepository turmaRepository,
			PontuacaoRepository pontuacaoRepository, RoleRepository roleRepository, UserRepository userRepository) {
		this.anoLetivoRepository = anoLetivoRepository;
		this.turmaRepository = turmaRepository;
		this.pontuacaoRepository = pontuacaoRepository;
		this.roleRepository = roleRepository;
		this.userRepository = userRepository;
	}

	@Override
	public void run(String... args) throws Exception {
//		Create Roles
		Role r1 = Role.builder().name(RoleEnum.ROLE_ADMIN.toString()).build();
		Role r2 = Role.builder().name(RoleEnum.ROLE_AVAL.toString()).build();
		Role r3 = Role.builder().name(RoleEnum.ROLE_SERV.toString()).build();
		Role r4 = Role.builder().name(RoleEnum.ROLE_ALUNO.toString()).build();
		roleRepository.saveAll(Arrays.asList(r1, r2, r3, r4));

//		Create User
		User u1 = User.builder().nome("Teste").matricula("0101").funcao(r1).build();
		userRepository.saveAll(Arrays.asList(u1));

//		Criar ano letivo
		AnoLetivo ano22 = AnoLetivo.builder().ano(2022).aberto(true).build();

		AnoLetivo ano23 = AnoLetivo.builder().ano(2023).aberto(true).build();
		anoLetivoRepository.saveAll(Arrays.asList(ano22, ano23));

//		Criar turmas
		Turma turmaA = Turma.builder().id(1L).anoLetivo(ano23).nome("ADM 1VA").descricao("Descricao da turma.").build();

		Turma turmaB = Turma.builder().id(2L).anoLetivo(ano23).nome("ADM 1VB").descricao("Descricao da turma.").build();

		Turma turmaC = Turma.builder().id(3L).anoLetivo(ano23).nome("INFO 1MA").descricao("Descricao da turma.")
				.build();

		Turma turmaD = Turma.builder().id(4L).anoLetivo(ano23).nome("INFO 2MA").descricao("Descricao da turma.")
				.build();

		Turma turmaE = Turma.builder().id(5L).anoLetivo(ano23).nome("ADM 4VA").descricao("Descricao da turma.").build();

		turmaRepository.saveAll(Arrays.asList(turmaA, turmaB, turmaC, turmaD, turmaE));

//		Criar pontuação
		Pontuacao pontTrA1 = Pontuacao.builder().contador(1).turma(turmaA).operacao(PontuacaoOperationEnum.SUM)
				.pontos(3).descricao("Descricao da pontuacao.").aplicado(true).anulado(false)
				.data(new Date(System.currentTimeMillis())).build();
		Pontuacao pontTrA2 = Pontuacao.builder().contador(2).turma(turmaA).operacao(PontuacaoOperationEnum.SUM)
				.pontos(10).descricao("Descricao da pontuacao.").aplicado(true).anulado(false)
				.data(new Date(System.currentTimeMillis())).build();
		Pontuacao pontTrA3 = Pontuacao.builder().contador(3).turma(turmaA).operacao(PontuacaoOperationEnum.SUB)
				.pontos(5).descricao("Descricao da pontuacao.").aplicado(true).anulado(false)
				.data(new Date(System.currentTimeMillis())).build();
		Pontuacao pontTrA4 = Pontuacao.builder().contador(4).turma(turmaA).operacao(PontuacaoOperationEnum.SUM)
				.pontos(4).descricao("Descricao da pontuacao.").aplicado(true).anulado(false)
				.data(new Date(System.currentTimeMillis())).build();
		Pontuacao pontTrA5 = Pontuacao.builder().contador(5).turma(turmaA).operacao(PontuacaoOperationEnum.SUM)
				.pontos(2).descricao("Descricao da pontuacao.").aplicado(true).anulado(false)
				.data(new Date(System.currentTimeMillis())).build();
		pontuacaoRepository.saveAll(Arrays.asList(pontTrA1, pontTrA2, pontTrA3, pontTrA4, pontTrA5));

		Pontuacao pontTrB1 = Pontuacao.builder().contador(1).turma(turmaB).operacao(PontuacaoOperationEnum.SUM)
				.pontos(2).descricao("Descricao da pontuacao.").aplicado(true).anulado(false)
				.data(new Date(System.currentTimeMillis())).build();
		Pontuacao pontTrB2 = Pontuacao.builder().contador(2).turma(turmaB).operacao(PontuacaoOperationEnum.SUM)
				.pontos(8).descricao("Descricao da pontuacao.").aplicado(true).anulado(false)
				.data(new Date(System.currentTimeMillis())).build();
		Pontuacao pontTrB3 = Pontuacao.builder().contador(3).turma(turmaB).operacao(PontuacaoOperationEnum.SUM)
				.pontos(5).descricao("Descricao da pontuacao.").aplicado(true).anulado(false)
				.data(new Date(System.currentTimeMillis())).build();
		Pontuacao pontTrB4 = Pontuacao.builder().contador(4).turma(turmaB).operacao(PontuacaoOperationEnum.SUB)
				.pontos(4).descricao("Descricao da pontuacao.").aplicado(true).anulado(false)
				.data(new Date(System.currentTimeMillis())).build();
		Pontuacao pontTrB5 = Pontuacao.builder().contador(6).turma(turmaB).operacao(PontuacaoOperationEnum.SUM)
				.pontos(1).descricao("Descricao da pontuacao.").aplicado(true).anulado(false)
				.data(new Date(System.currentTimeMillis())).build();
		pontuacaoRepository.saveAll(Arrays.asList(pontTrB1, pontTrB2, pontTrB3, pontTrB4, pontTrB5));

	}

}
