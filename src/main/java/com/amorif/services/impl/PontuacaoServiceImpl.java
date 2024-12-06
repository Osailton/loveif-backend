package com.amorif.services.impl;

import java.util.ArrayList;
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
import com.amorif.entities.TipoRegra;
import com.amorif.entities.Turma;
import com.amorif.entities.TurnoEnum;
import com.amorif.exceptions.AnnualRuleException;
import com.amorif.exceptions.BimonthlyRuleException;
import com.amorif.exceptions.InvalidBimesterException;
import com.amorif.exceptions.InvalidExtraBimesterException;
import com.amorif.exceptions.InvalidFixedValueException;
import com.amorif.exceptions.InvalidTurnException;
import com.amorif.exceptions.InvalidVariableValueException;
import com.amorif.exceptions.UserHasNoPermitedRoleException;
import com.amorif.repository.AnoLetivoRepository;
import com.amorif.repository.PontuacaoRepository;
import com.amorif.repository.RegraRepository;
import com.amorif.repository.TurmaRepository;
import com.amorif.services.PontuacaoService;

@Service
public class PontuacaoServiceImpl implements PontuacaoService {

	private PontuacaoRepository pontuacaoRepository;
	private TurmaRepository turmaRepository;
	private RegraRepository regraRepository;
	private AnoLetivoRepository anoLetivoRepository;

	public PontuacaoServiceImpl(PontuacaoRepository pontuacaoRepository, TurmaRepository turmaRepository,
			RegraRepository regraRepository, AnoLetivoRepository anoLetivoRepository) {
		this.pontuacaoRepository = pontuacaoRepository;
		this.turmaRepository = turmaRepository;
		this.regraRepository = regraRepository;
		this.anoLetivoRepository = anoLetivoRepository;
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
		Turma turma = this.turmaRepository.getReferenceById(dtoRequest.getIdTurma());
		Regra regra = this.regraRepository.getReferenceById(dtoRequest.getIdRegra());
		AnoLetivo anoAtual = this.anoLetivoRepository.getLastActiveAnoLetivo();

		if (turma != null) {

			checkUserPermissionToReleasePoints(regra);

			checkIfBimesterIsValid(dtoRequest.getBimestre());

			checkIfIsExtraBimesterValid(regra, dtoRequest.getBimestre());

			checkReleasedPoints(regra, dtoRequest.getPontos());
			
			checkPointsFrequency(regra, anoAtual, dtoRequest.getBimestre(), dtoRequest.getIdTurma());

			Integer count = this.pontuacaoRepository.contadorByTurma(turma);
			Pontuacao pontuacao = Pontuacao.builder().bimestre(dtoRequest.getBimestre()).pontos(dtoRequest.getPontos())
					.regra(regra).anoLetivo(anoAtual).motivacao(dtoRequest.getMotivacao()).turma(turma)
					.contador(count != null ? this.pontuacaoRepository.contadorByTurma(turma) + 1 : 1).aplicado(false)
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
				.anulado(pontuacao.isAnulado()).build();
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
				throw new AnnualRuleException(
						"A pontuação associada a essa regra é anual e já existe um registro desse tipo neste ano letivo para a turma selecionada.");
			}
		}

		if (freq == FrequenciaRegraEnum.BIMESTRAL) {
			if (pontuacaoRepository.existsByBimesterAndRule(bimestre, regra.getId(), turmaId)) {
				throw new BimonthlyRuleException(
						"A pontuação associada a essa regra é bimestral e já existe um registro desse tipo neste bimestre para a turma selecionada.");
			}
		}
	}

}
