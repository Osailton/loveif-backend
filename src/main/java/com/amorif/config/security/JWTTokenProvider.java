package com.amorif.config.security;

import java.util.Base64;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.amorif.dto.response.AuthenticationDtoResponse;
import com.amorif.exceptions.InvalidJWTAuthenticationException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class JWTTokenProvider {
	
	@Value("${JWT_SECRET_KEY}")
	private String secrectKey;
	
	@Value("${JWT_EXPIRE_LENGTH:3600000}")
	private long expireInMilliseconds = 3600000;
	
	@Value("${JWT_REFRESH_EXPIRE_LENGTH:10800000}")
	private long refresgTokenExpireInMilliseconds = 10800000;
	
	@Autowired
	private UserDetailsService userDetailsService;

	private Algorithm algorithm = null;
	
	public JWTTokenProvider(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}
	
	@PostConstruct
	protected void init() {
		this.secrectKey = Base64.getEncoder().encodeToString(this.secrectKey.getBytes());
		this.algorithm = Algorithm.HMAC256(this.secrectKey.getBytes());
	}
	
	public String getStringAccessToken(String matricula, List<String> roles) {
		Date now = new Date();
		Date validity = new Date(now.getTime() + this.expireInMilliseconds);
		return getAccessToken(matricula, roles, now, validity);
	}
	
	public String getStringRefreshToken(String matricula, List<String> roles) {
		Date now = new Date();
		return getRefreshToken(matricula, roles, now);
	}
	
	public AuthenticationDtoResponse createAccessToken(String matricula, List<String> roles) {
		Date now = new Date();
		Date validity = new Date(now.getTime() + this.expireInMilliseconds);
		var accessToken = getAccessToken(matricula, roles, now, validity);
		var refreshToken = getRefreshToken(matricula, roles, now);
		return AuthenticationDtoResponse.builder()
				.matricula(matricula)
				.accessToken(accessToken)
				.refreshToken(refreshToken)
				.build();
	}

	private String getRefreshToken(String matricula, List<String> roles, Date now) {
		Date validityRefreshToken = new Date(now.getTime() + this.refresgTokenExpireInMilliseconds);
		return JWT.create()
				.withClaim("roles", roles)
				.withIssuedAt(now)
				.withExpiresAt(validityRefreshToken)
				.withSubject(matricula)
				.sign(algorithm)
				.strip();
	}

	private String getAccessToken(String matricula, List<String> roles, Date now, Date validity) {
		String issuerUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
		return JWT.create()
				.withClaim("roles", roles)
				.withIssuedAt(now)
				.withExpiresAt(validity)
				.withSubject(matricula)
				.withIssuer(issuerUrl)
				.sign(algorithm)
				.strip();
	}
	
	public Authentication getAuthentication(String token) {
		DecodedJWT decodedJWT = decodedToken(token);
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(decodedJWT.getSubject());
		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	}

	private DecodedJWT decodedToken(String token) {
		Algorithm algorithm = Algorithm.HMAC256(this.secrectKey.getBytes());
		JWTVerifier verifier = JWT.require(algorithm).build();
		return verifier.verify(token);
	}
	
	public String resolveToken(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if(bearerToken != null && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring("Bearer ".length());
		}
		return null;
	}
	
	public boolean validateToken(String token) {
		try {
			DecodedJWT decodedJWT = decodedToken(token);
			if(decodedJWT.getExpiresAt().before(new Date())) {
				return false;
			}
			return true;
		} catch (Exception e) {
			throw new InvalidJWTAuthenticationException("Autenticação inválida!");
		}
	}
	
	public JWTTokenProvider(UserDetailsService userDetailsService, 
            @Value("${JWT_SECRET_KEY}") String secrectKey, 
            @Value("${JWT_EXPIRE_LENGTH:3600000}") long expireInMilliseconds, 
            @Value("${JWT_REFRESH_EXPIRE_LENGTH:10800000}") long refresgTokenExpireInMilliseconds) {
		this.userDetailsService = userDetailsService;
		this.secrectKey = Base64.getEncoder().encodeToString(secrectKey.getBytes());
		this.expireInMilliseconds = expireInMilliseconds;
		this.refresgTokenExpireInMilliseconds = refresgTokenExpireInMilliseconds;
		this.algorithm = Algorithm.HMAC256(this.secrectKey.getBytes());
	}
	
	public JWTTokenProvider() {
	}
}
