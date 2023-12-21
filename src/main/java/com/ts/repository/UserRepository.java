package com.ts.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ts.entity.Ticket;
import com.ts.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByEmail(String email);

	Boolean existsByEmail(String email);

//	User findByEmail(String email);

	Optional<User> findById(Long id);

	List<User> findByIdIn(List<Long> userIds);

}
