package com.ts.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.ts.entity.*;
import com.ts.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.ts.dto.ApiResponse;
import com.ts.dto.TicketRequest;
import com.ts.exception.TicketNotFoundException;
import com.ts.service.TicketService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/ticket")
public class TicketController {

	@Autowired
	UserRepository userRepository;

	@Autowired
	CommentRepository commentRepository;

	@Autowired
	ReplyRepository replyRepository;

	@Autowired
	TicketRepository ticketRepository;

	@Autowired
	CategoryRepository categoryRepository;

	private TicketService ticketService;

	private static final Logger log = LoggerFactory.getLogger(TicketController.class);

	@GetMapping("/{ticketId}")
	public ResponseEntity<Ticket> fetchTicket(@PathVariable Long ticketId) {
		System.out.println("Ticket Fetched");
		Ticket ticket = ticketRepository.getTicketByTicketId(ticketId);
			return ResponseEntity.ok(ticket);
	}

	@GetMapping("/{ticketId}/comments")
	public ResponseEntity<List<Comment>> fetchComments(@PathVariable Long ticketId) {
		Ticket ticket = ticketRepository.getTicketByTicketId(ticketId);
		List<Comment> comments = commentRepository.getCommentsByTicket(ticket);
		System.out.println("Comments Fetched");
		return ResponseEntity.ok(comments);
	}

	@GetMapping("/comment/{commentId}")
	public ResponseEntity<List<Reply>> fetchReplies(@PathVariable Long commentId) {
		Comment comment = commentRepository.getCommentById(commentId);
		List<Reply> replies = replyRepository.getRepliesByComment(comment);
//		List<Reply> replies = replyRepository.getByCommentId(commentId);
		System.out.println("Replies Fetched");
		return ResponseEntity.ok(replies);
	}

	@GetMapping("/comment/{commentId}/reply")
	public ResponseEntity<List<Reply>> fetchMyReplies(@PathVariable Long commentId) {
//		Comment comment = commentRepository.getCommentById(commentId);
//		List<Reply> replies = replyRepository.getRepliesByComment(comment);
		List<Reply> replies = replyRepository.getByCommentId(commentId);
		System.out.println("Replies Fetched");
		return ResponseEntity.ok(replies);
	}


	@PostMapping("/{ticketId}/comment")
	public String comment(@PathVariable Long ticketId, @RequestBody Comment comment){
		comment.setTicket(comment.getTicket());
		// comment.setTicket(ticketRepository.findById(ticketId).get());
		comment.setDate(java.time.LocalDateTime.now().toString());
		comment.setUser(comment.getUser());
		commentRepository.save(comment);
		return "Comment have been commented successfully";
	}

	@PostMapping("/comment/{commentId}/reply")
	public String reply(@PathVariable Long commentId, @RequestBody Reply reply){
		Comment comment = commentRepository.getCommentById(commentId);
		reply.setComment(comment);
//		reply.setComment(reply.getComment());
		// comment.setTicket(ticketRepository.findById(ticketId).get());
		reply.setDate(java.time.LocalDateTime.now().toString());
		reply.setUser(reply.getUser());
		replyRepository.save(reply);
		return "Reply have been replied successfully";
	}

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
