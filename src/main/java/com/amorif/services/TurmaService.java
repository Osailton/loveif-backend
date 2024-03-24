package com.amorif.services;

import java.util.List;

import com.amorif.dto.request.TurmaDtoRequest;
import com.amorif.dto.response.TurmaDtoResponse;

/**
 * @author osailton
 */
public interface TurmaService {
	
	List<TurmaDtoResponse> listAll();
	List<TurmaDtoResponse> listByAno(Long idAno);
	TurmaDtoResponse postTurma(TurmaDtoRequest turmaRequest);

}
