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
	
	public List<Ticket> getTickets() {
		return ticketRepository.findAll();
	}
}
