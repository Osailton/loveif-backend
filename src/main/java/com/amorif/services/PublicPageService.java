package com.amorif.services;

import java.util.List;

import com.amorif.dto.response.PontuacaoDtoResponse;
import com.amorif.dto.response.TurmaDtoResponse;

/**
 * @author osailton
 */

public interface PublicPageService {
	
	List<TurmaDtoResponse> listPontuacao();
	
	List<PontuacaoDtoResponse> listPontuacaoByTurma(Long turmaId);

}
