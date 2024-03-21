package com.amorif.services;

import java.util.List;

import com.amorif.dto.request.PontuacaoDtoRequest;
import com.amorif.dto.response.PontuacaoDtoResponse;

/**
 * @author osailton
 */
public interface PontuacaoService {
	
	List<PontuacaoDtoResponse> pontosByAno(Long idAno);
	PontuacaoDtoResponse throwPoints(PontuacaoDtoRequest dtoRequest);

}
