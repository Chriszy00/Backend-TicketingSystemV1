package com.ts.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ts.entity.Ticket;
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
	
	@GetMapping("/allTickets")
	public List<Ticket> getAllTickets() {
		return ticketRepository.findAll();
	}
	
	@PutMapping("/{id}/resolved")
	public ResponseEntity<String> resovledTicket(@PathVariable Long id) {
		Optional<Ticket> ticketOptional = ticketRepository.findById(id);
		if(ticketOptional.isPresent()) {
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
		if(ticketOptional.isPresent()) {
			Ticket status = ticketOptional.get();
			status.setStatus("Assigned");
			
			ticketRepository.save(status);
			
			return ResponseEntity.ok("Issue assigned succesfully");
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
