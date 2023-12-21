package com.ts.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ts.entity.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Long>{

	Optional<Ticket> findById(Long ticketId);
}
