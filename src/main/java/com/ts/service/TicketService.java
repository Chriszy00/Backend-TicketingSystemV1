package com.ts.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ts.entity.RoleName;
import com.ts.entity.Ticket;
import com.ts.entity.User;
import com.ts.exception.AppException;
import com.ts.repository.TicketRepository;

@Service
public class TicketService {

	@Autowired
	TicketRepository ticketRepository;
	
	@Autowired
	UserService userService;
	
//    private final TicketRepository ticketRepository;

    public void saveTicket(Ticket ticket) {
        // Add any additional business logic or validation if needed
        ticketRepository.save(ticket);
    }
    
	public List<Ticket> getTickets() {
		return ticketRepository.findAll();
	}
	
	public Ticket getTicketById(Long ticketId) {
		return ticketRepository.findById(ticketId).orElseThrow(() -> new AppException("Ticket not found."));
	}
	
    public List<Ticket> getTicketsForInternalUser(Long userId) {
        User internalUser = userService.getUserById(userId);

        if (internalUser == null || !internalUser.getRoles().stream()
                .anyMatch(role -> role.getName().equals(RoleName.ROLE_INTERNAL))) {
            throw new AppException("User is not an internal user.");
        }

        return ticketRepository.findByAssignedUser(internalUser);
    }
}
