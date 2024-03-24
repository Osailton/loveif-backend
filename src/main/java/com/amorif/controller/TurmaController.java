package com.amorif.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.amorif.dto.request.TurmaDtoRequest;
import com.amorif.dto.response.TurmaDtoResponse;
import com.amorif.services.TurmaService;

/**
 * @author osailton
 */

@RestController
@RequestMapping("/api/turma")
public class TurmaController {

	private TurmaService turmaService;

	public TurmaController(TurmaService turmaService) {
		this.turmaService = turmaService;
	}

//	Method to return a list of 'Turma'
	@GetMapping("turmas")
	public ResponseEntity<List<TurmaDtoResponse>> listTurmas(
			@RequestParam(value = "ano", defaultValue = "0") Long idAno) {
		if (idAno > 0) {
			return ResponseEntity.ok().body(this.turmaService.listByAno(idAno));
		} else {
			return ResponseEntity.ok().body(this.turmaService.listAll());
		}
	}

//	Create or update a 'AnoLetivo'
	@PostMapping("turma")
	public ResponseEntity<TurmaDtoResponse> postTurma(@RequestBody TurmaDtoRequest request) {
		return ResponseEntity.ok().body(this.turmaService.postTurma(request));
	}

}
