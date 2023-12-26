package com.ts.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.ts.entity.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Long>{

	Optional<Ticket> findById(Long ticketId);
	
	@Transactional
	@Modifying
	@Query("DELETE FROM Ticket t WHERE t.creator.id = :userId")
	void deleteByCreatorUserId(@Param("userId") Long userId);

//	@Query("SELECT t FROM Ticket t WHERE t.ticketId = :ticketId")
//	Ticket getById(Ticket ticketId);

	Ticket getTicketByTicketId(Long ticketId);

}
