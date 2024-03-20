package com.amorif.services.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.amorif.dto.request.TurmaDtoRequest;
import com.amorif.dto.response.AnoLetivoDtoResponse;
import com.amorif.dto.response.TurmaDtoResponse;
import com.amorif.entities.AnoLetivo;
import com.amorif.entities.Turma;
import com.amorif.exceptions.InvalidArgumentException;
import com.amorif.repository.AnoLetivoRepository;
import com.amorif.repository.TurmaRepository;
import com.amorif.services.ManagerService;

/**
 * @author osailton
 */

@Service
public class ManagerServiceImpl implements ManagerService {

	private final TurmaRepository turmaRepository;
	private final AnoLetivoRepository anoLetivoRepository;

	public ManagerServiceImpl(TurmaRepository turmaRepository, AnoLetivoRepository anoLetivoRepository) {
		this.turmaRepository = turmaRepository;
		this.anoLetivoRepository = anoLetivoRepository;
	}

	@Override
	public TurmaDtoResponse createTurma(TurmaDtoRequest request) {
		Optional<AnoLetivo> ano = Optional
				.ofNullable(this.anoLetivoRepository.getOpenAnoLetivoById(request.getIdAnoLetivo())
						.orElseThrow(() -> new InvalidArgumentException("O ano letivo não existe ou está encerrado!")));
		if (!ano.isEmpty()) {
			if (((AnoLetivo) ano.get()).getTurmas().stream().anyMatch(t -> t.getNome().contains(request.getNome()))) {
				throw new InvalidArgumentException(
						"Já existe uma turma cadastrada com esse nome para o ano letivo selecionado!");
			}
			Turma turma = Turma.builder().anoLetivo((AnoLetivo) ano.get()).nome(request.getNome())
					.descricao(request.getDescricao()).build();
			this.turmaRepository.save(turma);
			return turmaToTurmaDtoResponse(turma);
		} else {
			throw new InvalidArgumentException("Parâmetros inválidos para a requisição!");
		}
	}

	private TurmaDtoResponse turmaToTurmaDtoResponse(Turma turma) {
		return TurmaDtoResponse.builder()
				.anoLetivo(new AnoLetivoDtoResponse.Builder().id(turma.getAnoLetivo().getId())
						.anoLetivo(turma.getAnoLetivo().getAno()).build())
				.nome(turma.getNome()).descricao(turma.getDescricao()).pontuacao(0).build();
	}
}
