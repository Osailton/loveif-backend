package com.amorif.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.amorif.entities.Role;

/**
 * @author osailton
 */

public interface RoleRepository extends JpaRepository<Role, Long> {
	
	Role getByName(String name);

}
