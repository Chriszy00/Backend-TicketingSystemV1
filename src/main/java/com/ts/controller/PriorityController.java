package com.ts.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ts.entity.Priority;
import com.ts.repository.PriorityRepository;

@RestController
@RequestMapping("/api/priorities")
@CrossOrigin (origins = "http://localhost:3000")
public class PriorityController {

	@Autowired
	private PriorityRepository priorityRepository;
	
    @GetMapping
    public ResponseEntity<?> getAllPriority() {
        List<Priority> priority = priorityRepository.findAll();
        return ResponseEntity.ok(priority);
    }
}
