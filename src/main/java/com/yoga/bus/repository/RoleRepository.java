package com.yoga.bus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yoga.bus.models.ERole;
import com.yoga.bus.models.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{
	Role findByName(ERole name);
	Role findById(int id);
}