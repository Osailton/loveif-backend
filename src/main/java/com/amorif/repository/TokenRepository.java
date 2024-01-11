package com.amorif.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.amorif.entities.Token;

/**
 * @author osailton
 */

public interface TokenRepository extends JpaRepository<Token, Long> {
	
	Optional<Token> findByToken(String token);
	
	@Query(value = "SELECT t FROM Token t "
			+ "INNER JOIN User u "
			+ "ON t.user.id = u.id "
			+ "WHERE u.id = :id and (t.expired = false OR t.revoked = false)")
	List<Token> findAllValidTokensByUser(Long id);

}
