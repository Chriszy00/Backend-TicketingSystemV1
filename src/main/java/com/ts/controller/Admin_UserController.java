package com.ts.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ts.entity.Ticket;
import com.ts.entity.User;
import com.ts.exception.UserNotFoundException;
import com.ts.repository.TicketRepository;
import com.ts.repository.UserRepository;

@RestController
@CrossOrigin(origins = "http://localhost:3000/")
@RequestMapping("/user/management")
public class Admin_UserController {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	TicketRepository ticketRepository;
	
	@GetMapping("/allUsers")
	public List<User> getAllUsers(){
		return userRepository.findAll();
	}
	
    // Delete User along with related tickets
    @DeleteMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable Long id) {
        // Check if the user exists
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        // Delete related tickets
        ticketRepository.deleteByCreatorUserId(id);

        // Delete the user
        userRepository.deleteById(id);

        return "User with id: " + id + " has been deleted successfully";
    }
    
	@GetMapping("/allTickets")
	public List<Ticket> getAllTickets() {
		return ticketRepository.findAll();
	}
	
	@GetMapping("/userDetails/{id}")
	User getUserById(@PathVariable Long id) {
		return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
	}
}
