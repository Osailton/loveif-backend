package com.amorif.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author osailton
 */

@RestController
@RequestMapping("/api/pontuacao")
public class PointsController {
	
	@GetMapping("/")
	public String testController() {
		return "Hello!";
	}

}
