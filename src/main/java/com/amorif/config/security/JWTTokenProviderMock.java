package com.amorif.config.security;

import java.util.Collections;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;

@Primary
@Service
@Profile("test")
public class JWTTokenProviderMock extends JWTTokenProvider {

    public JWTTokenProviderMock(UserDetailsService userDetailsService) {
        super(userDetailsService, "test-secret", 3600000L, 10800000L);
    }

    @Override
    public Authentication getAuthentication(String token) {    	
        // Cria um usuário de teste com roles definidas
        User userDetails = new User("user", "password", 
                Collections.singleton(new SimpleGrantedAuthority("ROLE_BIBLIOTECARIO")));

        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }


    @Override
    public boolean validateToken(String token) {
        // Sempre retorna true para simplificar os testes
        return true;
    }

    @Override
    public String resolveToken(HttpServletRequest request) {
        return "dummy-token";
    }
}

