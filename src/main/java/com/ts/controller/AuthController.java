package com.ts.controller;

import java.util.Collections;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@CrossOrigin (origins = "http://localhost:3000")
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
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequest.getEmail(),
                    loginRequest.getPassword()
                )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = tokenProvider.generateToken(authentication);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String username = userDetails.getUsername();
            String roleName = userDetails.getAuthorities().iterator().next().getAuthority(); // Retrieve the role name

            User user = userRepository.findByEmail(username)
                    .orElseThrow(() -> new AppException("User not found."));
            Long userId = user.getId(); // Retrieve the user ID
            String email = username;

            JwtAuthenticationResponse response = new JwtAuthenticationResponse(jwt, roleName, userId, email);
            System.out.println("User ID: " + response.getUserId());

            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            // Handle authentication failure (e.g., invalid credentials)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse(false, "Invalid credentials"));
        } catch (AppException e) {
            // Handle custom application exception (e.g., user not found)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(false, e.getMessage()));
        } catch (Exception e) {
            // Handle other unexpected exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(false, "Internal server error"));
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> registerUser(@RequestBody SignUpRequest userRegistrationDTO) {
        // Validate input data...

        // Check if the selected role is valid
        Role userRole = roleRepository.findByName(userRegistrationDTO.getRole())
                .orElseThrow(() -> new AppException("Invalid role."));

        // Check if the email address is already taken
        if (userRepository.existsByEmail(userRegistrationDTO.getEmail())) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, "Email Address has already been taken"));
        }

        if (userRole == null) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, "Invalid Role"));
        }
        
        // Encode the password
        String encodedPassword = passwordEncoder.encode(userRegistrationDTO.getPassword());

        // Create a new User entity and save it to the database
        User user = new User(
                userRegistrationDTO.getFirstName(),
                userRegistrationDTO.getLastName(),
                userRegistrationDTO.getEmail(),
                encodedPassword // Use the encoded password
        );

        user.setRoles(Collections.singleton(userRole));
        userRepository.save(user);

        return ResponseEntity.ok(new ApiResponse(true, "User registered successfully."));
    }
}
