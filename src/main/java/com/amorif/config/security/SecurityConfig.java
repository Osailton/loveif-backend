package com.amorif.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.amorif.entities.RoleEnum;

/**
 * @author osailton
 */

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private final String[] WHITE_LIST = new String[] { "/api/auth/**", "/api/public/**" };

	private final String[] AVAL_LIST = new String[] { "/api/pontuacao/**" };

	private final String[] MANAGE_LIST = new String[] { "/api/manager/**" };

	private final AuthenticationProvider authenticationProvider;
	private final JWTAuthenticationFilter jwtAuthenticationFilter;

	public SecurityConfig(AuthenticationProvider authenticationProvider,
			JWTAuthenticationFilter jwtAuthenticationFilter) {
		this.authenticationProvider = authenticationProvider;
		this.jwtAuthenticationFilter = jwtAuthenticationFilter;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity.httpBasic((basic) -> basic.disable()).csrf((csrf) -> csrf.disable())
				.sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests((auth) -> auth.requestMatchers(WHITE_LIST).permitAll().requestMatchers(AVAL_LIST)
						.hasAnyRole(this.getRole(RoleEnum.ROLE_ADMIN.toString()),
								this.getRole(RoleEnum.ROLE_AVAL.toString()))
						.requestMatchers(MANAGE_LIST).hasAnyRole(this.getRole(RoleEnum.ROLE_ADMIN.toString()))
						.requestMatchers("/users").denyAll().anyRequest().authenticated())
				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
				.authenticationProvider(this.authenticationProvider).build();
	}

	private String getRole(String role) {
		return role.substring("ROLE_".length());
	}
}
