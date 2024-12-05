package com.amorif.config;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.amorif.dto.response.ErrorMessageDtoResponse;
import com.amorif.exceptions.AnnualRuleException;
import com.amorif.exceptions.BimonthlyRuleException;
import com.amorif.exceptions.InvalidArgumentException;
import com.amorif.exceptions.InvalidBimesterException;
import com.amorif.exceptions.InvalidExtraBimesterException;
import com.amorif.exceptions.InvalidFixedValueException;
import com.amorif.exceptions.InvalidJWTAuthenticationException;
import com.amorif.exceptions.InvalidVariableValueException;
import com.amorif.exceptions.UserAlreadyExistsException;
import com.amorif.exceptions.UserHasNoPermitedRoleException;

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
	
	@ExceptionHandler(UserHasNoPermitedRoleException.class)
	public ResponseEntity<Object> handleUserHasNoPermitedRoleException(UserHasNoPermitedRoleException e, WebRequest request) {
		ErrorMessageDtoResponse eMessage = new ErrorMessageDtoResponse(HttpStatus.FORBIDDEN, e.getMessage());
		return new ResponseEntity<Object>(eMessage, new HttpHeaders(), eMessage.getStatus());
	}
	
	@ExceptionHandler(InvalidBimesterException.class)
	public ResponseEntity<Object> handleInvalidBimesterException(InvalidBimesterException e, WebRequest request) {
		ErrorMessageDtoResponse eMessage = new ErrorMessageDtoResponse(HttpStatus.BAD_REQUEST, e.getMessage());
		return new ResponseEntity<Object>(eMessage, new HttpHeaders(), eMessage.getStatus());
	}	
	
	@ExceptionHandler(InvalidExtraBimesterException.class)
	public ResponseEntity<Object> handleInvalidExtraBimesterException(InvalidExtraBimesterException e, WebRequest request) {
		ErrorMessageDtoResponse eMessage = new ErrorMessageDtoResponse(HttpStatus.BAD_REQUEST, e.getMessage());
		return new ResponseEntity<Object>(eMessage, new HttpHeaders(), eMessage.getStatus());
	}	
	
	@ExceptionHandler(InvalidFixedValueException.class)
	public ResponseEntity<Object> handleInvalidFixedValueException(InvalidFixedValueException e, WebRequest request) {
		ErrorMessageDtoResponse eMessage = new ErrorMessageDtoResponse(HttpStatus.BAD_REQUEST, e.getMessage());
		return new ResponseEntity<Object>(eMessage, new HttpHeaders(), eMessage.getStatus());
	}	
	
	@ExceptionHandler(InvalidVariableValueException.class)
	public ResponseEntity<Object> handleInvalidVariableValueException(InvalidVariableValueException e, WebRequest request) {
		ErrorMessageDtoResponse eMessage = new ErrorMessageDtoResponse(HttpStatus.BAD_REQUEST, e.getMessage());
		return new ResponseEntity<Object>(eMessage, new HttpHeaders(), eMessage.getStatus());
	}
	
	@ExceptionHandler(AnnualRuleException.class)
	public ResponseEntity<Object> handleAnnualRuleException(AnnualRuleException e, WebRequest request) {
		ErrorMessageDtoResponse eMessage = new ErrorMessageDtoResponse(HttpStatus.BAD_REQUEST, e.getMessage());
		return new ResponseEntity<Object>(eMessage, new HttpHeaders(), eMessage.getStatus());
	}
	
	@ExceptionHandler(BimonthlyRuleException.class)
	public ResponseEntity<Object> handleBimonthlyRuleException(BimonthlyRuleException e, WebRequest request) {
		ErrorMessageDtoResponse eMessage = new ErrorMessageDtoResponse(HttpStatus.BAD_REQUEST, e.getMessage());
		return new ResponseEntity<Object>(eMessage, new HttpHeaders(), eMessage.getStatus());
	}

}
