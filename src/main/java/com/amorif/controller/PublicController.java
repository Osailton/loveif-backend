package com.amorif.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PublicController {
	
	@RequestMapping("/hello")
	public String hello() {
		return "Hello, world!";
	}
}
