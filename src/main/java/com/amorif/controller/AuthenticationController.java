package com.amorif.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.amorif.dto.request.RegisterDtoRequest;
import com.amorif.dto.request.SUAPUserDtoRequest;
import com.amorif.services.AuthService;

import jakarta.servlet.http.HttpServletRequest;

import java.net.URISyntaxException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

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
	
	@GetMapping("user")
	public ResponseEntity<?> user(HttpServletRequest request) {
//		Get user from the token
		return ResponseEntity.ok().body(this.authService.getUserFromToken(request));
	}
	

}
