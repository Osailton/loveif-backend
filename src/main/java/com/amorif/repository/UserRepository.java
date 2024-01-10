package com.amorif.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.amorif.entities.User;

/**
 * @author osailton
 */

public interface UserRepository extends JpaRepository<User, Long> {
	
	Optional<User> findByMatricula(String matricula);

}
