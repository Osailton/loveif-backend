package com.amorif.services;

import java.util.List;

import com.amorif.dto.request.AnoLetivoDtoRequest;
import com.amorif.dto.response.AnoLetivoDtoResponse;

/**
 * @author osailton
 */
public interface AnoLetivoService {
	
	List<AnoLetivoDtoResponse> listAll();
	AnoLetivoDtoResponse postAnoLetivo(AnoLetivoDtoRequest anoLetivoRequest);

}
