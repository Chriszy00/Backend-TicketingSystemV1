package com.ts.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ts.entity.RoleName;
import com.ts.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByEmail(String email);

	Boolean existsByEmail(String email);
	
	List<User> findByRoles_Name(RoleName roleName);

//	User findByEmail(String email);

	Optional<User> findById(Long userId);

	List<User> findByIdIn(List<Long> userIds);

	@Query(value = "SELECT * FROM users JOIN user_roles ON users.id = user_roles.user_id JOIN roles ON user_roles.role_id = roles.id WHERE roles.name = 'ROLE_USER'\n", nativeQuery = true)
	List<User> findUsersByRoleUser();

}
