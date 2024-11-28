package com.amorif.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.amorif.dto.request.PontuacaoDtoRequest;
import com.amorif.dto.response.PontuacaoDtoResponse;
import com.amorif.entities.BimestreEnum;
import com.amorif.entities.Pontuacao;
import com.amorif.entities.Regra;
import com.amorif.entities.Turma;
import com.amorif.exceptions.InvalidBimesterException;
import com.amorif.exceptions.UserHasNoPermitedRoleException;
import com.amorif.repository.PontuacaoRepository;
import com.amorif.repository.RegraRepository;
import com.amorif.repository.TurmaRepository;
import com.amorif.services.PontuacaoService;

@Service
public class PontuacaoServiceImpl implements PontuacaoService {
	
	private PontuacaoRepository pontuacaoRepository;
	private TurmaRepository turmaRepository;
	private RegraRepository regraRepository;
	
	public PontuacaoServiceImpl(PontuacaoRepository pontuacaoRepository, TurmaRepository turmaRepository, RegraRepository regraRepository) {
		this.pontuacaoRepository = pontuacaoRepository;
		this.turmaRepository = turmaRepository;
		this.regraRepository = regraRepository;
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
		System.out.println(dtoRequest);
		Turma turma = this.turmaRepository.getReferenceById(dtoRequest.getIdTurma());
		Regra regra = this.regraRepository.getReferenceById(dtoRequest.getIdRegra());
		
		if(turma != null) {

	        if (!userHasPermission(regra)) {
	            throw new UserHasNoPermitedRoleException("Usuário não tem permissão para lançar esta pontuação.");
	        }
	        
	        if (!bimesterIsValid(dtoRequest.getBimestre())) {
	        	throw new InvalidBimesterException("Bimestre inválido");
	        }
			
			Integer count = this.pontuacaoRepository.contadorByTurma(turma);
			Pontuacao pontuacao = Pontuacao.builder()
					.pontos(dtoRequest.getPontos())
					.regra(regra)
					.motivacao(dtoRequest.getMotivacao())
					.turma(turma)
					.contador(count != null ? this.pontuacaoRepository.contadorByTurma(turma) + 1 : 1)
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
				.descricao(pontuacao.getMotivacao())
				.pontos(pontuacao.getPontos())
				.operacao(pontuacao.getRegra().getOperacao())
				.aplicado(pontuacao.isAplicado())
				.anulado(pontuacao.isAnulado())
				.build();
	}
	
	private boolean userHasPermission(Regra regra) {
		// Obtenha o usuário autenticado
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        // Verifique os papéis do usuário
        List<String> userRoles = userDetails.getAuthorities().stream()
            .map(authority -> authority.getAuthority())
            .collect(Collectors.toList());

        // Verifique se algum dos papéis do usuário está permitido na regra
        boolean hasPermission = regra.getRoles().stream()
            .anyMatch(role -> userRoles.contains(role.getName()));
        
        return hasPermission;
	}
	
	private boolean bimesterIsValid(Integer bimestre) {
		return bimestre >= 0 && bimestre < BimestreEnum.values().length;
	}

}
