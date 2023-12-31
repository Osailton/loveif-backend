package com.amorif;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author osailton
 */

@SpringBootApplication
public class StartApp {

	@Value("${ALLOWED_CORS}")
	private String allowedCORS;

	public static void main(String[] args) {
		SpringApplication.run(StartApp.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/*").allowedOrigins(allowedCORS);
			}
		};
	}

}
