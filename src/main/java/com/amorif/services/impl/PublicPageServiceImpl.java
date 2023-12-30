package com.amorif.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.amorif.dto.response.TurmaDtoResponse;
import com.amorif.entities.AnoLetivo;
import com.amorif.entities.Turma;
import com.amorif.repository.AnoLetivoRepository;
import com.amorif.repository.PontuacaoRepository;
import com.amorif.repository.TurmaRepository;
import com.amorif.services.PublicPageService;

/**
 * @author osailton
 */

@Service
public class PublicPageServiceImpl implements PublicPageService {

	private final PontuacaoRepository pontuacaoRepository;
	private final TurmaRepository turmaRepository;
	private final AnoLetivoRepository anoLetivoRepository;

	public PublicPageServiceImpl(PontuacaoRepository pontuacaoRepository, TurmaRepository turmaRepository,
			AnoLetivoRepository anoLetivoRepository) {
		this.pontuacaoRepository = pontuacaoRepository;
		this.turmaRepository = turmaRepository;
		this.anoLetivoRepository = anoLetivoRepository;
	}

	@Override
	public List<TurmaDtoResponse> listPontuacao() {
		// Instantiate the response list
		List<TurmaDtoResponse> listResponse = new ArrayList<>();

		// Get the last active AnoLetivo
		AnoLetivo anoLetivo = this.anoLetivoRepository.getLastActiveAnoLetivo();
		System.out.println("ANO: " + anoLetivo.getAno());

		// Get a list of Turma for that AnoLetivo
		List<Turma> turmas = this.turmaRepository.findAllByAnoLetivo(anoLetivo);

		// For each Turma get the sum of positive and negative points, creating TurmaDto
		for (Turma t : turmas) {
			int points = this.pontuacaoRepository.positivePoints(t) - this.pontuacaoRepository.negativePoints(t);
			TurmaDtoResponse dto = this.turmaToDto(t);
			dto.setPontuacao(points);
			listResponse.add(dto);
		}

		return listResponse;
	}

	private TurmaDtoResponse turmaToDto(Turma turma) {
		TurmaDtoResponse turmaDto = TurmaDtoResponse.builder().anoLetivo(turma.getAnoLetivo().getAno())
				.nome(turma.getNome()).descricao(turma.getDescricao()).build();
		return turmaDto;
	}

}
