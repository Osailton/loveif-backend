package com.amorif.config.security;

import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.amorif.entities.User;

import jakarta.servlet.http.HttpServletRequest;

@Profile("test")
@Service
public class JWTTokenProviderMock extends JWTTokenProvider {

    public JWTTokenProviderMock(UserDetailsService userDetailsService) {
        super(userDetailsService, "test-secret", 3600000L, 10800000L);
    }

    @Override
    public Authentication getAuthentication(String token) {
        // Cria um usuário de teste com roles definidas
        User userDetails = User.builder()
                .matricula("testUser")
                .nome("Test User")
                .email("testuser@example.com")
                //.funcoes(Set.of(new Role("ROLE_TEST")))  // Roles de exemplo, substituíveis conforme necessário
                .build();

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

