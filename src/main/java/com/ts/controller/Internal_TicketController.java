package com.ts.controller;

import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ts.entity.RoleName;
import com.ts.entity.Ticket;
import com.ts.entity.User;
import com.ts.repository.RoleRepository;
import com.ts.repository.TicketRepository;
import com.ts.repository.UserRepository;
import com.ts.service.TicketService;
import com.ts.service.UserService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/ticket/management")
public class Internal_TicketController {

	@Autowired
	UserRepository userRepository;

	@Autowired
	TicketRepository ticketRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	UserService userService;

	@Autowired
	TicketService ticketService;

	/*
	 * @GetMapping("/allTickets") public List<Ticket> getAllTickets() { return
	 * ticketRepository.findAll(); }
	 */

	@PutMapping("/{id}/resolved")
	public ResponseEntity<String> resovledTicket(@PathVariable Long id) {
		Optional<Ticket> ticketOptional = ticketRepository.findById(id);
		if (ticketOptional.isPresent()) {
			Ticket status = ticketOptional.get();
			status.setStatus("Resolved");

			ticketRepository.save(status);

			return ResponseEntity.ok("Issue resolved succesfully");
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PutMapping("/{id}/assigned")
	public ResponseEntity<String> assignedTicket(@PathVariable Long id) {
		Optional<Ticket> ticketOptional = ticketRepository.findById(id);
		if (ticketOptional.isPresent()) {
			Ticket status = ticketOptional.get();
			status.setStatus("Assigned");

			ticketRepository.save(status);

			return ResponseEntity.ok("Issue assigned succesfully");
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/internal-users")
	public List<User> getInternalUsers() {
		return userRepository.findByRoles_Name(RoleName.ROLE_INTERNAL);
	}

	@PostMapping("/{ticketId}/assigned/{assignedUserId}")
	public ResponseEntity<String> assignTicketToUserWithRoleInternal(@PathVariable Long ticketId,
	        @PathVariable Long assignedUserId, @RequestBody Map<String, Long> requestBody) {
	    try {
	        // Check if the ticket and user exist
	        Ticket ticket = ticketService.getTicketById(ticketId);
	        User assignedUser = userService.getUserById(assignedUserId);

	        if (ticket == null || assignedUser == null) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ticket or user not found");
	        }

	        // Check if the user has the required role ("ROLE_INTERNAL")
	        if (!assignedUser.getRoles().stream().anyMatch(role -> role.getName().equals(RoleName.ROLE_INTERNAL))) {
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User does not have the required role");
	        }

	        // Assign the ticket to the user by updating the status
	        ticket.setAssignedUser(assignedUser);
	        ticket.setStatus("Assigned");
	        // Optionally, you may set other fields related to the assignment

	        ticketRepository.save(ticket);

	        return ResponseEntity.ok("Ticket assigned successfully");
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error assigning ticket");
	    }
	}
	
	@GetMapping("/internal-user-tickets")
	public ResponseEntity<List<Ticket>> getTicketsForInternalUser(Principal principal) {
	    try {
	        // Get the logged-in user's id
	        Long userId = Long.parseLong(principal.getName());

	        // Fetch the user by id
	        User currentUser = userService.getUserById(userId);

	        if (currentUser.getRoles().stream().anyMatch(role -> role.getName().equals(RoleName.ROLE_INTERNAL))) {
	            // User has ROLE_INTERNAL, fetch assigned tickets for the internal user
	            List<Ticket> tickets = ticketService.getTicketsForInternalUser(userId);
	            return ResponseEntity.ok(tickets);
	        } else {
	            // User does not have ROLE_INTERNAL, return an empty list or handle accordingly
	            return ResponseEntity.ok(Collections.emptyList());
	        }
	    } catch (NumberFormatException e) {
	        // Handle the case where the userId is not a valid Long
	        return ResponseEntity.badRequest().body(null);
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	    }
	}







//	@PostMapping("/assignTicket/{ticketId}")
////	@PreAuthorize("hasRole('ROLE_INTERNAL')")
//	public ResponseEntity<String> assignTicket(@PathVariable Long ticketId,
//			@RequestBody Map<String, Long> requestBody) {
//		Long assignedUserId = requestBody.get("assignedUserId");
//
//		Ticket ticket = ticketService.getTicketById(ticketId);
//		User assignedUser = userService.getUserById(assignedUserId);
//
//		if (ticket == null || assignedUser == null) {
//			return ResponseEntity.badRequest().body("Invalid ticket ID or assigned user ID");
//		}
//
//		// Check if the assigned user has the required role
//		if (!assignedUser.getRoles().stream().anyMatch(role -> role.getName().equals(RoleName.ROLE_INTERNAL))) {
//			return ResponseEntity.badRequest().body("Invalid assigned user ID or user does not have the required role");
//		}
//
//		// Update ticket information
//		ticket.setAssignedUser(assignedUser);
//		ticket.setStatus("Assigned");
//
//		// Save the updated ticket
//		ticketService.saveTicket(ticket);
//
//		return ResponseEntity.ok("Ticket assigned successfully");
//	}

}
