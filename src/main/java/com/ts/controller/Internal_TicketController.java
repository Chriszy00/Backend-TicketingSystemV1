package com.ts.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ts.entity.RoleName;
import com.ts.entity.Ticket;
import com.ts.entity.User;
import com.ts.repository.RoleRepository;
import com.ts.repository.TicketRepository;
import com.ts.repository.UserRepository;

@RestController
@CrossOrigin(origins = "http://localhost:3000/")
@RequestMapping("/ticket/management")
public class Internal_TicketController {

	@Autowired
	UserRepository userRepository;

	@Autowired
	TicketRepository ticketRepository;

	@Autowired
	RoleRepository roleRepository;

	@GetMapping("/allTickets")
	public List<Ticket> getAllTickets() {
		return ticketRepository.findAll();
	}

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

    @PutMapping("/{ticketId}/assigned/{userId}")
    public ResponseEntity<String> assignTicketToUserWithRoleInternal(
            @PathVariable Long ticketId,
            @PathVariable Long userId) {

        try {
            // Check if the ticket and user exist
            Optional<Ticket> optionalTicket = ticketRepository.findById(ticketId);
            Optional<User> optionalUser = userRepository.findById(userId);

            if (optionalTicket.isEmpty() || optionalUser.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ticket or user not found");
            }

            Ticket ticket = optionalTicket.get();
            User user = optionalUser.get();

            // Check if the user has the required role ("ROLE_INTERNAL")
            if (!user.getRoles().stream().anyMatch(role -> role.getName().equals(RoleName.ROLE_INTERNAL))) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User does not have the required role");
            }

            // Assign the ticket to the user by updating the status
            ticket.setStatus("Assigned");
            // Optionally, you may set other fields related to the assignment

            ticketRepository.save(ticket);

            return ResponseEntity.ok("Ticket assigned successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error assigning ticket");
        }
    }

}
