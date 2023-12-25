package com.ts.service;

import com.ts.entity.Message;
import com.ts.entity.User;
import com.ts.repository.MessageRepository;
import com.ts.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
    }

    public Message createMessage(User sender, String messageText) {

        // Create a new message
        Message message = new Message();
        message.setSender(sender);
//        message.setReceiver(receiver);
        message.setMessage(messageText);
        message.setDateTime(java.time.LocalDateTime.now().toString());

        // Save the message to the database
        return messageRepository.save(message);
    }

    public Message createMessage(Message message) {

        Message saved = new Message();

        saved.setMessage(message.getMessage());
        System.out.println(message.getMessage());
        saved.setSender(message.getSender());
        saved.setDateTime(java.time.LocalDateTime.now().toString());

        return messageRepository.save(saved);
    }

    public Message createReply(Message message) {

        Message saved = new Message();

        saved.setMessage(message.getMessage());
        saved.setType(message.getType());
        saved.setReceiver(message.getReceiver());
        saved.setSender(message.getSender());
        saved.setDateTime(java.time.LocalDateTime.now().toString());

        return messageRepository.save(saved);
    }

    public List<Message> getMessageHistory() {
        return messageRepository.findAll();
    }

    public List<Message> getMessagesBetweenSenderOrReceiver(Long loggedUser, Long userId) {
        return messageRepository.findMessagesBetweenSenderOrReceiver(loggedUser, userId);
    }

    public List<Message> getMessageByUser(Long userId) {
        // Assuming you have a method in your repository to find messages by sender or receiver ID
        return messageRepository.findMessagesByUser(userId);
    }
}


