package com.amorif.services;

import java.util.List;

import com.amorif.dto.request.PontuacaoDtoRequest;
import com.amorif.dto.response.PontuacaoDtoResponse;

/**
 * @author osailton
 */
public interface PontuacaoService {
	
	List<PontuacaoDtoResponse> pontosByAno(Long idAno);
	List<PontuacaoDtoResponse> pontosByLastActiveYear();
	List<PontuacaoDtoResponse> pointsToValidate();
	List<PontuacaoDtoResponse> appliedPointsOfLastActiveYear();
	List<PontuacaoDtoResponse> cancelledPointsOfLastActiveYear();
	List<PontuacaoDtoResponse> throwPoints(PontuacaoDtoRequest dtoRequest);
	List<PontuacaoDtoResponse> throwAutoPoints(PontuacaoDtoRequest dtoRequest);
	List<PontuacaoDtoResponse> pontosByLoggedUser();
	void deletePontuacao(PontuacaoDtoRequest dtoRequest);

}
