package com.amorif.services;

import com.amorif.dto.request.TurmaDtoRequest;
import com.amorif.dto.response.TurmaDtoResponse;

/**
 * @author osailton
 */

public interface ManagerService {
	
	TurmaDtoResponse createTurma(TurmaDtoRequest request);

}
