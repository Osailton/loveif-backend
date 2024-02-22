package com.amorif.services;

import com.amorif.dto.request.RegisterDtoRequest;
import com.amorif.dto.request.SUAPUserDtoRequest;
import com.amorif.dto.response.AuthenticationDtoResponse;

import jakarta.servlet.http.HttpServletRequest;

/**
 * @author osailton
 */

public interface AuthService {

	AuthenticationDtoResponse getUserFromToken(HttpServletRequest request);
	SUAPUserDtoRequest getSuapUser(String token);
	RegisterDtoRequest getTokenFromCode(String code);
	boolean existsByMatricula(String matricula);
	AuthenticationDtoResponse register(SUAPUserDtoRequest dto);
	AuthenticationDtoResponse authenticate(SUAPUserDtoRequest dto);

}
