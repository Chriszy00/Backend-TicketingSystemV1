package com.ts.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ts.entity.Category;
import com.ts.repository.CategoryRepository;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin (origins = "http://localhost:3000")
public class CategoryController {

	@Autowired
	private CategoryRepository categoryRepository;
	
    @GetMapping
    public ResponseEntity<?> getAllRoles() {
        List<Category> category = categoryRepository.findAll();
        return ResponseEntity.ok(category);
    }
}
