package com.ts.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ts.entity.User;
import com.ts.exception.AppException;
import com.ts.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired 
	UserRepository userRepository;
	
	public User getUserById(Long userId) {
		return userRepository.findById(userId).orElseThrow(() -> new AppException("User not found."));
	}
	
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException("User not found."));
    }
}
