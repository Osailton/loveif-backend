package com.amorif;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author osailton
 */

@SpringBootApplication
public class StartApp {

	@Value("${ALLOWED_CORS}")
	private String allowedCORS;
	
	@Value("${ALLOWED_CORS_PROD}")
    private String allowedCORSProd;

	public static void main(String[] args) {
		SpringApplication.run(StartApp.class, args);
	}

	@Bean
	@Profile({"dev", "test"})
	public WebMvcConfigurer corsConfigurerDev() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/*").allowedOrigins(allowedCORS);
			}
		};
	}
	
	@Bean
	@Profile("prod")
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                // Separando as origens caso haja múltiplas
                String[] allowedOrigins = allowedCORSProd.split(",");
                registry.addMapping("/*")
                        .allowedOrigins(allowedOrigins)  // Permitindo múltiplas origens
                        .allowedMethods("GET", "POST", "PUT", "DELETE")  // Métodos permitidos
                        .allowedHeaders("*")  // Permitindo todos os cabeçalhos
                        .allowCredentials(true);  // Permitindo credenciais (cookies, headers de autenticação)
            }
        };
    }

}
