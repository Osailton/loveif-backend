package com.amorif.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amorif.dto.response.TurmaDtoResponse;
import com.amorif.services.PublicPageService;

/**
 * @author osailton
 */

@RestController
@RequestMapping("/api/public")
public class PublicController {
	
	private final PublicPageService publicPageService;
	
	public PublicController(PublicPageService publicPageService) {
		this.publicPageService = publicPageService;
	}

//	Method to return a list of 'Turma' with total points
//	This must be for the last AnoLetivo in wich 'aberto' is true
	@GetMapping("pontuacao")
	public ResponseEntity<List<TurmaDtoResponse>> listPoints() {
		return ResponseEntity.ok().body(this.publicPageService.listPontuacao());
	}
}
