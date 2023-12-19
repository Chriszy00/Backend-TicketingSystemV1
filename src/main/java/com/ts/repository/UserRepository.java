package com.ts.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ts.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByEmail(String email);

	Boolean existsByEmail(String email);

	Boolean existsByUsername(String username);

	Optional<User> findByUsernameOrEmail(String username, String usernameOrEmail);

	Optional<User> findByUsername(String username);

	List<User> findByIdIn(List<Long> userIds);

}
