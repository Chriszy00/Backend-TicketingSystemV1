package com.ts.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ts.entity.User;
import com.ts.repository.UserRepository;

@RestController
@CrossOrigin(origins = "http://localhost:3000/")
@RequestMapping("/user/management")
public class Admin_UserController {

	@Autowired
	UserRepository userRepository;
	
	@GetMapping("/allUsers")
	public List<User> getAllUsers(){
		return userRepository.findAll();
	}
}
