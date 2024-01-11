package com.amorif.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amorif.dto.request.TurmaDtoRequest;
import com.amorif.dto.response.TurmaDtoResponse;
import com.amorif.services.ManagerService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


/**
 * @author osailton
 */

@RestController
@RequestMapping("/api/manager")
public class ManagerController {
	
	private final ManagerService managerService;
	
	public ManagerController(ManagerService managerService) {
		this.managerService = managerService;
	}
	
	@PostMapping("turma")
	public ResponseEntity<TurmaDtoResponse> register(@RequestBody TurmaDtoRequest request) {
		// Create new turma
		return ResponseEntity.ok().body(this.managerService.createTurma(request));
	}
	

}
