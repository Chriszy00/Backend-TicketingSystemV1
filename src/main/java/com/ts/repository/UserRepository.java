package com.ts.repository;

import java.util.List;
import java.util.Optional;

import com.ts.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ts.entity.Ticket;
import com.ts.entity.User;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByEmail(String email);

	Boolean existsByEmail(String email);

//	User findByEmail(String email);

	Optional<User> findById(Long id);

	List<User> findByIdIn(List<Long> userIds);

	@Query(value = "SELECT * FROM users JOIN user_roles ON users.id = user_roles.user_id JOIN roles ON user_roles.role_id = roles.id WHERE roles.name = 'ROLE_USER'\n", nativeQuery = true)
	List<User> findUsersByRoleUser();

}
