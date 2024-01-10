package com.amorif.services;

import com.amorif.dto.request.SUAPUserDtoRequest;
import com.amorif.dto.response.AuthenticationDtoResponse;

/**
 * @author osailton
 */

public interface AuthService {

	SUAPUserDtoRequest getSuapUser(String token);
	boolean existsByMatricula(String matricula);
	AuthenticationDtoResponse register(SUAPUserDtoRequest dto);

}
