package com.ts.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.ts.entity.Ticket;
import com.ts.entity.User;

public interface TicketRepository extends JpaRepository<Ticket, Long>{

	Optional<Ticket> findById(Long ticketId);
	List<Ticket> findByAssignedUser(User assignedUser);
	
	@Transactional
	@Modifying
	@Query("DELETE FROM Ticket t WHERE t.creator.id = :userId")
	void deleteByCreatorUserId(@Param("userId") Long userId);

}
