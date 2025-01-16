package com.amorif.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.amorif.dto.request.PontuacaoDtoRequest;
import com.amorif.dto.response.PontuacaoDtoResponse;
import com.amorif.services.PontuacaoService;

/**
 * @author osailton
 */

@RestController
@RequestMapping("/api/pontuacao")
public class PointsController {
	
	private PontuacaoService pontuacaoService;
	
	public PointsController(PontuacaoService pontuacaoService) {
		this.pontuacaoService = pontuacaoService;
	}
	
	@GetMapping("pontuacaoPorServidor")
	public ResponseEntity<List<PontuacaoDtoResponse>> pontosByLoggedUser() {
		return ResponseEntity.ok().body(this.pontuacaoService.pontosByLoggedUser());
	}
	
	@GetMapping("pontos")
	public ResponseEntity<List<PontuacaoDtoResponse>> pontosByAno(@RequestParam(name = "ano", defaultValue = "0") Long idAno) {
		return ResponseEntity.ok().body(this.pontuacaoService.pontosByAno(idAno));
	}
	
	@GetMapping("pontosDoAnoCorrente")
	public ResponseEntity<List<PontuacaoDtoResponse>> pontosByLastActiveYear() {
		return ResponseEntity.ok().body(this.pontuacaoService.pontosByLastActiveYear());
	}
	
	@GetMapping("pontosDoAnoCorrenteParaValidar")
	public ResponseEntity<List<PontuacaoDtoResponse>> pointsToValidate() {
		return ResponseEntity.ok().body(this.pontuacaoService.pointsToValidate());
	}
	
	@GetMapping("pontosDoAnoCorrenteAplicados")
	public ResponseEntity<List<PontuacaoDtoResponse>> appliedPointsOfLastActiveYear() {
		return ResponseEntity.ok().body(this.pontuacaoService.appliedPointsOfLastActiveYear());
	}
	
	@GetMapping("pontosDoAnoCorrenteAnulados")
	public ResponseEntity<List<PontuacaoDtoResponse>> cancelledPointsOfLastActiveYear() {
		return ResponseEntity.ok().body(this.pontuacaoService.cancelledPointsOfLastActiveYear());
	}
	
	@PostMapping("lancar")
	public ResponseEntity<List<PontuacaoDtoResponse>> throwPoints(@RequestBody PontuacaoDtoRequest request) {
		return ResponseEntity.ok().body(this.pontuacaoService.throwPoints(request));
	}
	
	@PostMapping("lancarPontosAutomaticos")
	public ResponseEntity<List<PontuacaoDtoResponse>> throwAutoPoints(@RequestBody PontuacaoDtoRequest request) {
		return ResponseEntity.ok().body(this.pontuacaoService.throwAutoPoints(request));
	}
	
	@PostMapping("deletarPontuacao")
	public ResponseEntity<String> deletePoints(@RequestBody PontuacaoDtoRequest request) {
		this.pontuacaoService.deletePontuacao(request);
		return ResponseEntity.ok().body("Pontuação deletada");
	}

}
