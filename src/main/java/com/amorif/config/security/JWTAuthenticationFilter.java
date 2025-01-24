package com.amorif.config.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.amorif.entities.User;
import com.amorif.repository.TokenRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * @author osailton
 */

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

	private final JWTTokenProvider jwtTokenProvider;
	private final UserDetailsService userDetailsService;
	private final TokenRepository tokenRepository;
	
	public JWTAuthenticationFilter(JWTTokenProvider jwtTokenProvider, UserDetailsService userDetailsService, TokenRepository tokenRepository) {
		this.jwtTokenProvider = jwtTokenProvider;
		this.userDetailsService = userDetailsService;
		this.tokenRepository = tokenRepository;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String token = this.jwtTokenProvider.resolveToken(request);
		
		// If this conditions matches, aka is not valid token, go to the next filter
		if(token == null || !this.jwtTokenProvider.validateToken(token)) {
			filterChain.doFilter(request, response);
			return;
		}
		
		String username = this.jwtTokenProvider.getAuthentication(token).getName();			
		if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			User user = (User) this.userDetailsService.loadUserByUsername(username);
			boolean isTokenValid = this.tokenRepository.findByToken(token)
					.map(tk -> !tk.isExpired() && !tk.isRevoked())
					.orElse(false);
			if(this.jwtTokenProvider.validateToken(token) && isTokenValid) {
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		}
		
		filterChain.doFilter(request, response);
	}

}
