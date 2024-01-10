package com.amorif.dto.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;

public class ErrorMessageDtoResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private HttpStatus status;
	private List<String> errors;

	public ErrorMessageDtoResponse(HttpStatus status, List<String> errors) {
		this.status = status;
		this.errors = errors;
	}

	public ErrorMessageDtoResponse(HttpStatus status, String error) {
		this.status = status;
		this.errors = new ArrayList<>();
		this.errors.add(error);
	}

	public void appendMessage(String error) {
		this.errors.add(error);
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}
}