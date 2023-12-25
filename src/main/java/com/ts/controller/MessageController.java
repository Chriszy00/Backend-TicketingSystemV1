package com.ts.controller;

import com.ts.entity.Message;
import com.ts.entity.User;
import com.ts.repository.UserRepository;
import com.ts.service.MessageService;
import com.ts.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/message")
public class MessageController {

    private final MessageService messageService;

    private final UserRepository userRepository;

    @Autowired
    public MessageController(MessageService messageService, UserService userService, UserRepository userRepository) {
        this.messageService = messageService;
        this.userRepository = userRepository;
    }

    @GetMapping("/convo")
    public ResponseEntity<List<Message>> getConversation(@RequestParam Long userId) {
        // Implement logic to fetch conversation messages for the given userId
        try {
            List<Message> historicalMessages = messageService.getMessageByUser(userId);
            return ResponseEntity.ok(historicalMessages);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        try {
            List<User> users = userRepository.findUsersByRoleUser();
            return ResponseEntity.ok(users);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}

