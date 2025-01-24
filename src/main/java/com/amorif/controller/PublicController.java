package com.amorif.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.amorif.dto.response.PontuacaoDtoResponse;
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
	
	@GetMapping("pontuacaoPorTurma")
	public ResponseEntity<List<PontuacaoDtoResponse>> listPointsByTurma(@RequestParam(name = "turmaId", defaultValue = "0") Long turmaId) {
		return ResponseEntity.ok().body(this.publicPageService.listPontuacaoByTurma(turmaId));	
	}
	
//	Redirect test
	@GetMapping("red")
	public ResponseEntity<List<TurmaDtoResponse>> redirect() throws URISyntaxException {
		URI externalUri = new URI("http://localhost:3000/regiment");
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(externalUri);
        return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
	}
}
