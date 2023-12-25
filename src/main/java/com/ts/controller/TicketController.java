package com.ts.controller;

import java.util.Collections;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ts.dto.ApiResponse;
import com.ts.dto.TicketRequest;
import com.ts.entity.Category;
import com.ts.entity.CategoryName;
import com.ts.entity.Ticket;
import com.ts.entity.User;
import com.ts.exception.TicketNotFoundException;
import com.ts.repository.CategoryRepository;
import com.ts.repository.TicketRepository;
import com.ts.repository.UserRepository;
import com.ts.service.TicketService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/ticket")
public class TicketController {

	@Autowired
	UserRepository userRepository;

	@Autowired
	TicketRepository ticketRepository;

	@Autowired
	CategoryRepository categoryRepository;

	private TicketService ticketService;

	private static final Logger log = LoggerFactory.getLogger(TicketController.class);

	@PostMapping("/complaint")
	public ResponseEntity<?> submitTicket(@Valid @RequestBody TicketRequest ticketRequest,
			@AuthenticationPrincipal UserDetails userDetails) {
		try {
			// Check if userDetails is not null
			if (userDetails != null) {
				// Retrieve user from the authenticated user
				Optional<User> optionalUser = userRepository.findByEmail(userDetails.getUsername());

				if (optionalUser.isPresent()) {
					User user = optionalUser.get();

					// Retrieve selected category from the request
					CategoryName categoryName = ticketRequest.getCategoryNames();

					// Create a new Ticket
					Ticket ticket = new Ticket();
					ticket.setTitle(ticketRequest.getTitle());
					ticket.setDescription(ticketRequest.getDescription());
					ticket.setCreator(user);
					ticket.setStatus("Pending");

					// Associate the category with the ticket
					Category category = categoryRepository.findByName(categoryName)
							.orElseThrow(() -> new RuntimeException("Category not found with name: " + categoryName));
					ticket.setCategory_id(category);

					// Add the category to the Set<Category> as well, if needed
					ticket.getCategories().add(category);

					// Set other ticket properties as needed

					ticketRepository.save(ticket);

					return ResponseEntity.ok("Ticket submitted successfully");
				} else {
					return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(false, "User not found"));
				}
			} else {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
						.body(new ApiResponse(false, "User not authenticated"));
			}
		} catch (DataAccessException e) {
			// Log the exception
			log.error("Database error", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponse(false, "Database error: " + e.getMessage()));
		} catch (RuntimeException e) {
			// Log the exception
			log.error("Internal server error", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponse(false, "Internal server error: " + e.getMessage()));
		}
	}

	// Edit User; Need to clarify if we can edit the ticket
	/*
	 * @PutMapping("/updateUser/{id}") Ticket updateTicket
	 */
	@DeleteMapping("/deleteTicket/{id}")
	public String deleteTicket(@PathVariable Long id) {
		if (!ticketRepository.existsById(id)) {
			throw new TicketNotFoundException(id);
		}
		ticketRepository.deleteById(id);
		return "Ticket with id: " + id + " has been deleted successfully";
	}
}
