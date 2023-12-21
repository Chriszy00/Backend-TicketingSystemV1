package com.ts.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ts.entity.Ticket;
import com.ts.repository.TicketRepository;

@Service
public class TicketService {

	@Autowired
	TicketRepository ticketRepository;
	
//    private final TicketRepository ticketRepository;

    public void saveTicket(Ticket ticket) {
        // Add any additional business logic or validation if needed
        ticketRepository.save(ticket);
    }
    
	public List<Ticket> getTickets() {
		return ticketRepository.findAll();
	}
}
