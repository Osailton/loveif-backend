package com.amorif.services.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.amorif.dto.request.PontuacaoDtoRequest;
import com.amorif.dto.response.PontuacaoDtoResponse;
import com.amorif.entities.AnoLetivo;
import com.amorif.entities.BimestreEnum;
import com.amorif.entities.FrequenciaRegraEnum;
import com.amorif.entities.Pontuacao;
import com.amorif.entities.Regra;
import com.amorif.entities.User;
import com.amorif.entities.TipoRegra;
import com.amorif.entities.Turma;
import com.amorif.entities.TurnoEnum;
import com.amorif.exceptions.AnnualRuleException;
import com.amorif.exceptions.AnnualRulePerStudentException;
import com.amorif.exceptions.BimonthlyRuleException;
import com.amorif.exceptions.BimonthlyRulePerStudentException;
import com.amorif.exceptions.InvalidBimesterException;
import com.amorif.exceptions.InvalidExtraBimesterException;
import com.amorif.exceptions.InvalidFixedValueException;
import com.amorif.exceptions.InvalidSchoolRegistrationException;
import com.amorif.exceptions.InvalidTurnException;
import com.amorif.exceptions.InvalidVariableValueException;
import com.amorif.exceptions.RuleNotFoundException;
import com.amorif.exceptions.UserHasNoPermitedRoleException;
import com.amorif.repository.AnoLetivoRepository;
import com.amorif.repository.PontuacaoRepository;
import com.amorif.repository.RegraRepository;
import com.amorif.repository.TurmaRepository;
import com.amorif.repository.UserRepository;
import com.amorif.services.PontuacaoService;

@Service
public class PontuacaoServiceImpl implements PontuacaoService {

	private PontuacaoRepository pontuacaoRepository;
	private TurmaRepository turmaRepository;
	private RegraRepository regraRepository;
	private AnoLetivoRepository anoLetivoRepository;
	private UserRepository userRepository;

	public PontuacaoServiceImpl(PontuacaoRepository pontuacaoRepository, TurmaRepository turmaRepository,
			RegraRepository regraRepository, AnoLetivoRepository anoLetivoRepository, UserRepository userRepository) {
		this.pontuacaoRepository = pontuacaoRepository;
		this.turmaRepository = turmaRepository;
		this.regraRepository = regraRepository;
		this.anoLetivoRepository = anoLetivoRepository;
		this.userRepository = userRepository;
	}

	@Override
	public List<PontuacaoDtoResponse> pontosByAno(Long idAno) {
		if (idAno > 0) {
			return this.pontuacaoRepository.pontosByAno(idAno).stream().map(e -> dtoFromPontuacao(e))
					.collect(Collectors.toList());
		}
		return null;
	}
	
	@Override
	public List<PontuacaoDtoResponse> throwAutoPoints(PontuacaoDtoRequest dtoRequest) {
		List<PontuacaoDtoResponse> pontuacoes = new ArrayList<PontuacaoDtoResponse>();
		
		// Lista de Regras Automáticas
		Regra regraAutomaticaOrdenacao = regraRepository.findById(11L)
			    .orElseThrow(() -> new RuleNotFoundException("Regra com ID 11 não encontrada"));
			Regra regraAutomaticaLimpeza = regraRepository.findById(18L)
			    .orElseThrow(() -> new RuleNotFoundException("Regra com ID 18 não encontrada"));
		List<Regra> regrasAutomaticas = Arrays.asList(regraAutomaticaOrdenacao, regraAutomaticaLimpeza);
		
		// Lista de turmas qualificadas em cada regra
		List<List<Turma>> turmasQualificadas = new ArrayList<List<Turma>>();
		
		for (Regra regra : regrasAutomaticas) {
			turmasQualificadas.add(getTurmasParaBonus(regra.getId(), dtoRequest.getBimestre()));
		}
		
		System.out.println(turmasQualificadas);
		
		// Lançamento de pontuacoes		
		int index = 0;
		for (List<Turma> turmasQualificadasPorRegra : turmasQualificadas) {			
			
			for (Turma turma : turmasQualificadasPorRegra) {
				Regra regraAtual = regrasAutomaticas.get(index);
				
				dtoRequest.setIdTurma(turma.getId());
				dtoRequest.setIdRegra(regraAtual.getId());
				dtoRequest.setPontos(regraAtual.getValorMinimo());
				dtoRequest.setOperacao(regraAtual.getOperacao());
				dtoRequest.setMotivacao(regraAtual.getSenso().getDescricao() + " " + regraAtual.getDescricao());
				
				PontuacaoDtoResponse response = throwPointsForOne(dtoRequest);
				pontuacoes.add(response);
			}
			
			index++;
		}

		return pontuacoes;
	}

	@Override
	public List<PontuacaoDtoResponse> throwPoints(PontuacaoDtoRequest dtoRequest) {
		List<PontuacaoDtoResponse> pontuacoes = new ArrayList<PontuacaoDtoResponse>();
		Regra regra = this.regraRepository.getReferenceById(dtoRequest.getIdRegra());
		TipoRegra tipoRegra = regra.getTipoRegra();

		if (tipoRegra.isPorTurno()) {

			Integer turno = dtoRequest.getTurno();

			if (turno != null && turno >= 0 && turno < TurnoEnum.values().length) {
				List<Turma> turmas = turmaRepository.findAllByTurno(turno);

				for (Turma turma : turmas) {
					dtoRequest.setIdTurma(turma.getId());
					PontuacaoDtoResponse response = throwPointsForOne(dtoRequest);
					pontuacoes.add(response);
				}
			} else {
				throw new InvalidTurnException("O atributo turno é nulo ou não foi passado corretamente");
			}
		} else {
			pontuacoes.add(throwPointsForOne(dtoRequest));
		}

		return pontuacoes;
	}

	public PontuacaoDtoResponse throwPointsForOne(PontuacaoDtoRequest dtoRequest) {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = userRepository.findByMatricula(userDetails.getUsername()).get();
		Turma turma = this.turmaRepository.getReferenceById(dtoRequest.getIdTurma());
		Regra regra = this.regraRepository.getReferenceById(dtoRequest.getIdRegra());
		AnoLetivo anoAtual = this.anoLetivoRepository.getLastActiveAnoLetivo();

		System.out.println(regra);
		if (turma != null && regra != null) {
			

			checkUserPermissionToReleasePoints(regra);

			checkIfBimesterIsValid(dtoRequest.getBimestre());

			checkIfIsExtraBimesterValid(regra, dtoRequest.getBimestre());

			checkReleasedPoints(regra, dtoRequest.getPontos());
			

			if (regra.getTipoRegra().isTemAluno()) {
				checkPointsFrequencyPerStudent(regra, anoAtual, dtoRequest.getBimestre(), dtoRequest.getIdTurma(),
						dtoRequest.getMatriculaAluno());
			} else {
				checkPointsFrequency(regra, anoAtual, dtoRequest.getBimestre(), dtoRequest.getIdTurma());
			}

			Integer count = this.pontuacaoRepository.contadorByTurma(turma);
			Pontuacao pontuacao = Pontuacao.builder()
					.bimestre(dtoRequest.getBimestre())
					.pontos(dtoRequest.getPontos())
					.regra(regra)
					.anoLetivo(anoAtual)
					.motivacao(dtoRequest.getMotivacao())
					.turma(turma)
					.matriculaAluno(dtoRequest.getMatriculaAluno())
					.contador(count != null ? this.pontuacaoRepository.contadorByTurma(turma) + 1 : 1)
					.user(user)
					.data(new Date())
					.aplicado(false)
					.anulado(false).build();

			return this.dtoFromPontuacao(this.pontuacaoRepository.save(pontuacao));
		}

		return null;
	}

	private PontuacaoDtoResponse dtoFromPontuacao(Pontuacao pontuacao) {
		return PontuacaoDtoResponse.builder().bimestre(pontuacao.getBimestre()).contador(pontuacao.getContador())
				.nomeTurma(pontuacao.getTurma().getNome()).idTurma(pontuacao.getTurma().getId())
				.descricao(pontuacao.getMotivacao()).pontos(pontuacao.getPontos())
				.operacao(pontuacao.getRegra().getOperacao()).aplicado(pontuacao.isAplicado())
				.anulado(pontuacao.isAnulado()).matriculaAluno(pontuacao.getMatriculaAluno()).build();
	}

	private boolean userHasPermission(Regra regra) {
		// Obtenha o usuário autenticado
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		// Verifique os papéis do usuário
		List<String> userRoles = userDetails.getAuthorities().stream().map(authority -> authority.getAuthority())
				.collect(Collectors.toList());

		// Verifique se algum dos papéis do usuário está permitido na regra
		boolean hasPermission = regra.getRoles().stream().anyMatch(role -> userRoles.contains(role.getName()));

		return hasPermission;
	}

	private boolean bimesterIsValid(Integer bimestre) {
		return bimestre >= 0 && bimestre < BimestreEnum.values().length;
	}

	private boolean isExtraBimesterValid(Integer bimestre) {
		return bimestre == BimestreEnum.BI_EXTRA.ordinal() ? true : false;
	}

	public static boolean isSchoolRegistrationValid(String matricula) {
		// Verifica se a matrícula tem exatamente 15 caracteres
		if (matricula == null || matricula.length() < 14) {
			return false;
		}

		// Verifica se todos os caracteres são numéricos
		if (!matricula.matches("\\d+")) {
			return false;
		}

		return true;
	}

	private void checkReleasedPoints(Regra regra, Integer pontos) {
		if (regra.getTipoRegra().isFixo()) {

			Integer valorFixo = regra.getValorMinimo();

			if (valorFixo != pontos) {
				throw new InvalidFixedValueException("Os pontos lançados não correspondem ao valor fixo da regra");
			}

		} else {

			Integer valorMinimo = regra.getValorMinimo();
			Integer valorMaximo = regra.getValorMaximo();

			if (pontos < valorMinimo || pontos > valorMaximo) {
				throw new InvalidVariableValueException("Os pontos lançados não estão no intervalo da regra");
			}

		}
	}

	private void checkUserPermissionToReleasePoints(Regra regra) {
		if (!userHasPermission(regra)) {
			throw new UserHasNoPermitedRoleException("Usuário não tem permissão para lançar esta pontuação.");
		}
	}

	private void checkIfBimesterIsValid(Integer bimestre) {
		if (!bimesterIsValid(bimestre)) {
			throw new InvalidBimesterException("Bimestre inválido.");
		}
	}

	private void checkIfIsExtraBimesterValid(Regra regra, Integer bimestre) {
		if (regra.getTipoRegra().isBimestreExtra()) {
			if (!isExtraBimesterValid(bimestre)) {
				throw new InvalidExtraBimesterException(
						"A pontuação para essa regra deve ser lançada em um bimestre extra.");
			}
		}
	}

	private void checkPointsFrequency(Regra regra, AnoLetivo anoAtual, Integer bimestre, Long turmaId) {
		FrequenciaRegraEnum freq = FrequenciaRegraEnum.values()[regra.getTipoRegra().getFrequencia()];

		if (freq == FrequenciaRegraEnum.ANUAL) {
			if (pontuacaoRepository.existsByYearAndRule(anoAtual.getId(), regra.getId(), turmaId)) {
				throw new AnnualRuleException("Já existe uma pontuação anual para esta turma no ano letivo atual.");
			}
		}

		if (freq == FrequenciaRegraEnum.BIMESTRAL) {
			if (pontuacaoRepository.existsByBimesterAndRule(bimestre, regra.getId(), turmaId)) {
				throw new BimonthlyRuleException(
						"Já existe uma pontuação bimestral para esta turma no bimestre fornecido.");
			}
		}
	}

	private void checkPointsFrequencyPerStudent(Regra regra, AnoLetivo anoAtual, Integer bimestre, Long turmaId,
			String matriculaAluno) {

		if (!isSchoolRegistrationValid(matriculaAluno)) {
			throw new InvalidSchoolRegistrationException("Matrícula inválida");
		}

		FrequenciaRegraEnum freq = FrequenciaRegraEnum.values()[regra.getTipoRegra().getFrequencia()];

		if (freq == FrequenciaRegraEnum.ANUAL) {
			if (pontuacaoRepository.existsByYearAndRulePerStudent(anoAtual.getId(), regra.getId(), turmaId,
					matriculaAluno)) {
				throw new AnnualRulePerStudentException(
						"Já existe uma pontuação anual por aluno para esta turma e matrícula no ano letivo atual.");
			}
		}

		if (freq == FrequenciaRegraEnum.BIMESTRAL) {
			if (pontuacaoRepository.existsByBimesterAndRulePerStudent(bimestre, regra.getId(), turmaId,
					matriculaAluno)) {
				throw new BimonthlyRulePerStudentException(
						"Já existe uma pontuação bimestral por aluno para esta turma e matrícula no bimestre fornecido.");
			}
		}
	}
	
	
	public List<Turma> getTurmasParaBonus(Long regraId, Integer bimestre) {
		List<Turma> turmas = turmaRepository.findTurmasQualificadasParaBonus(regraId);
		System.out.println(turmas);
	    return null;
	}


}
