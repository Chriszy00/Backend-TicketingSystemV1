package com.ts.repository;

import com.ts.entity.Comment;
import com.ts.entity.Reply;
import com.ts.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("SELECT c FROM Comment c WHERE c.ticket = :ticketId")
    List<Comment> getByTicketId(Long ticketId);

    List<Comment> getCommentsByTicket(Ticket ticket);

    Comment getCommentById(Long commentId);
}
