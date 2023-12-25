package com.ts.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ts.entity.Priority;
import com.ts.entity.PriorityName;

public interface PriorityRepository extends JpaRepository<Priority, Long>{

	Priority findByName(String name);
	
	Optional<Priority> findByName(PriorityName priorityName);
}
