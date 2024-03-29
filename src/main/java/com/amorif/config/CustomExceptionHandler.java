package com.amorif.config;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.amorif.dto.response.ErrorMessageDtoResponse;
import com.amorif.exceptions.InvalidArgumentException;
import com.amorif.exceptions.InvalidJWTAuthenticationException;
import com.amorif.exceptions.UserAlreadyExistsException;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(UserAlreadyExistsException.class)
	public ResponseEntity<Object> handleUsernameAvailableException(UserAlreadyExistsException e, WebRequest request) {
		ErrorMessageDtoResponse eMessage = new ErrorMessageDtoResponse(HttpStatus.BAD_REQUEST, e.getMessage());
		return new ResponseEntity<Object>(eMessage, new HttpHeaders(), eMessage.getStatus());
	}
	
	@ExceptionHandler(InvalidJWTAuthenticationException.class)
	public ResponseEntity<Object> handleUsernameAvailableException(InvalidJWTAuthenticationException e, WebRequest request) {
		ErrorMessageDtoResponse eMessage = new ErrorMessageDtoResponse(HttpStatus.BAD_REQUEST, e.getMessage());
		return new ResponseEntity<Object>(eMessage, new HttpHeaders(), eMessage.getStatus());
	}
	
	@ExceptionHandler(InvalidArgumentException.class)
	public ResponseEntity<Object> handleInvalidArgumentException(InvalidArgumentException e, WebRequest request) {
		ErrorMessageDtoResponse eMessage = new ErrorMessageDtoResponse(HttpStatus.BAD_REQUEST, e.getMessage());
		return new ResponseEntity<Object>(eMessage, new HttpHeaders(), eMessage.getStatus());
	}

}
