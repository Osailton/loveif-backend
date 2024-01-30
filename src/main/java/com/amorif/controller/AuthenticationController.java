package com.amorif.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.amorif.dto.request.RegisterDtoRequest;
import com.amorif.dto.request.SUAPUserDtoRequest;
import com.amorif.exceptions.UserAlreadyExistsException;
import com.amorif.services.AuthService;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author osailton
 */

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

	private final AuthService authService;

	public AuthenticationController(AuthService authService) {
		this.authService = authService;
	}

	@GetMapping("register")
	public ResponseEntity<?> register(@RequestParam("code") String code) throws URISyntaxException {
//		Get token from code
		RegisterDtoRequest requestDto = this.authService.getTokenFromCode(code);

//		Get user from SUAP by token
		SUAPUserDtoRequest dto = this.authService.getSuapUser(requestDto.getToken());

//		Check if user is already registered
		if (this.authService.existsByMatricula(dto.getMatricula())) {
//			if it exists, authenticate the user
			return ResponseEntity.ok().body(this.authService.authenticate(dto));
		}

//		Register the new user cause if doesn't exist in the DB
		return ResponseEntity.ok().body(this.authService.register(dto));
	}

}
