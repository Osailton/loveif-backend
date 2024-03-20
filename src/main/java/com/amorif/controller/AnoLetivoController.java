package com.amorif.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amorif.dto.request.AnoLetivoDtoRequest;
import com.amorif.dto.response.AnoLetivoDtoResponse;
import com.amorif.services.AnoLetivoService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


/**
 * @author osailton
 */

@RestController
@RequestMapping("/api/anoletivo")
public class AnoLetivoController {

	private AnoLetivoService anoLetivoService;

	public AnoLetivoController(AnoLetivoService anoLetivoService) {
		this.anoLetivoService = anoLetivoService;
	}

//	Method to return a list of 'AnoLetivo'
	@GetMapping("anos")
	public ResponseEntity<List<AnoLetivoDtoResponse>> listPoints() {
		return ResponseEntity.ok().body(this.anoLetivoService.listAll());
	}
	
//	Create or update a 'AnoLetivo'
	@PostMapping("ano")
	public ResponseEntity<AnoLetivoDtoResponse> postAnoLetivo(@RequestBody AnoLetivoDtoRequest request) {
		return ResponseEntity.ok().body(this.anoLetivoService.postAnoLetivo(request));
	}

}
