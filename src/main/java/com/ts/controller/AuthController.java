package com.ts.controller;

import java.util.Collections;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ts.dto.ApiResponse;
import com.ts.dto.JwtAuthenticationResponse;
import com.ts.dto.LoginRequest;
import com.ts.dto.SignUpRequest;
import com.ts.entity.Role;
import com.ts.entity.RoleName;
import com.ts.entity.User;
import com.ts.exception.AppException;
import com.ts.repository.RoleRepository;
import com.ts.repository.UserRepository;
import com.ts.security.JwtTokenProvider;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	JwtTokenProvider tokenProvider;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsernameOrEmail(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);

		String jwt = tokenProvider.generateToken(authentication);
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		String username = userDetails.getUsername();
		String roleName = userDetails.getAuthorities().iterator().next().getAuthority();

		User user = userRepository.findByUsernameOrEmail(username, loginRequest.getUsernameOrEmail())
				.orElseThrow(() -> new AppException("User not found."));
		Long userId = user.getId();
		String email = username;

		JwtAuthenticationResponse response = new JwtAuthenticationResponse(jwt, roleName, userId, email);
		System.out.println("User ID: " + response.getUserId());

		return ResponseEntity.ok(response);
	}

	@PostMapping("/signup")
	public ResponseEntity<ApiResponse> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity.badRequest().body(new ApiResponse(false, "Username is already taken!"));
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity.badRequest().body(new ApiResponse(false, "Email Address already in use!"));
		}

		// Creating user's account

		User user = new User(signUpRequest.getFirstName(), signUpRequest.getLastName(), signUpRequest.getUsername(),
				signUpRequest.getEmail(), signUpRequest.getPassword());

		user.setPassowrd(passwordEncoder.encode(user.getPassowrd()));

		Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
				.orElseThrow(() -> new AppException("User Role not set."));
		
		user.setRoles(Collections.singleton(userRole));
        userRepository.save(user);

        return ResponseEntity.ok(new ApiResponse(true, "User registered successfully."));

	}

}
