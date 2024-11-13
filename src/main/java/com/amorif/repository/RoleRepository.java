package com.amorif.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.amorif.entities.Role;

/**
 * @author osailton
 */

public interface RoleRepository extends JpaRepository<Role, Long> {
	
	Role getByName(String name);
	
	List<Role> findByNameIn(List<String> names);

}
