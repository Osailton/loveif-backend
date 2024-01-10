package com.amorif.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.amorif.entities.Token;

/**
 * @author osailton
 */

public interface TokenRepository extends JpaRepository<Token, Long> {
	
	Optional<Token> findByToken(String token);

}
