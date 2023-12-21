package com.ts.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ts.entity.Category;
import com.ts.entity.CategoryName;

public interface CategoryRepository extends JpaRepository<Category, Long>{

	Category findByName(String name);
	
	Optional<Category> findByName(CategoryName categoryName);
}
