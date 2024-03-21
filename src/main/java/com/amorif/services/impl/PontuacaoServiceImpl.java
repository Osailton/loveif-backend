package com.amorif.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.amorif.dto.request.PontuacaoDtoRequest;
import com.amorif.dto.response.PontuacaoDtoResponse;
import com.amorif.entities.Pontuacao;
import com.amorif.entities.PontuacaoOperationEnum;
import com.amorif.entities.Turma;
import com.amorif.repository.PontuacaoRepository;
import com.amorif.repository.TurmaRepository;
import com.amorif.services.PontuacaoService;

@Service
public class PontuacaoServiceImpl implements PontuacaoService {
	
	private PontuacaoRepository pontuacaoRepository;
	private TurmaRepository turmaRepository;
	
	public PontuacaoServiceImpl(PontuacaoRepository pontuacaoRepository, TurmaRepository turmaRepository) {
		this.pontuacaoRepository = pontuacaoRepository;
		this.turmaRepository = turmaRepository;
	}

	@Override
	public List<PontuacaoDtoResponse> pontosByAno(Long idAno) {
		if(idAno > 0) {
			return this.pontuacaoRepository.pontosByAno(idAno).stream().map(e -> dtoFromPontuacao(e)).collect(Collectors.toList());
		}
		return null;
	}

	@Override
	public PontuacaoDtoResponse throwPoints(PontuacaoDtoRequest dtoRequest) {
		Turma turma = this.turmaRepository.getReferenceById(dtoRequest.getIdTurma());
		if(turma != null) {
			Pontuacao pontuacao = Pontuacao.builder()
					.pontos(dtoRequest.getPontos())
					.operacao(dtoRequest.getOperacao().equals("SUM") ? PontuacaoOperationEnum.SUM : PontuacaoOperationEnum.SUB)
					.descricao(dtoRequest.getDescricao())
					.turma(turma)
					.contador(this.pontuacaoRepository.contadorByTurma(turma) + 1)
					.aplicado(false)
					.anulado(false)
					.build();
			
			return this.dtoFromPontuacao(this.pontuacaoRepository.save(pontuacao));
		}
		return null;
	}

	private PontuacaoDtoResponse dtoFromPontuacao(Pontuacao pontuacao) {
		return PontuacaoDtoResponse.builder()
				.contador(pontuacao.getContador())
				.nomeTurma(pontuacao.getTurma().getNome())
				.idTurma(pontuacao.getTurma().getId())
				.descricao(pontuacao.getDescricao())
				.pontos(pontuacao.getPontos())
				.operacao(pontuacao.getOperacao().toString())
				.aplicado(pontuacao.isAplicado())
				.anulado(pontuacao.isAnulado())
				.build();
	}

}
