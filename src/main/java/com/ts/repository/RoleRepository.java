package com.ts.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ts.entity.Role;
import com.ts.entity.RoleName;


public interface RoleRepository extends JpaRepository<Role, Long> {

	Role findByName(String name);

	Optional<Role> findByName(RoleName roleName);
}
