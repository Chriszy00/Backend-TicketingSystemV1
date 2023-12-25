package com.ts.controller;

import com.ts.entity.Message;
import com.ts.repository.MessageRepository;
import com.ts.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Controller
@CrossOrigin(origins = "http://localhost:3000")
public class WebSocketController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/send/message")
    @SendTo("/topic/public")
    public Message sendMessage(@Payload Message message) {
        messageService.createMessage(message);
        return message;
    }

    @GetMapping("/fetch")
    public List<Message> getAllMessage(Long userId) {
        return messageService.getMessageByUser(userId);
    }

    @MessageMapping("/send/reply")
    @SendTo("/topic/public")
    public Message sendReply(@Payload Message message) {
        messageService.createReply(message);
        return message;
    }

//    @MessageMapping("/fetch/history")
//    public List<Message> fetchMessageHistory(@RequestParam Map<String, Object> params) {
//        Long userId = Long.parseLong(params.get("userId").toString());
//        List<Message> historicalMessages = messageService.getMessageByUser(userId);
//
//        // When sending historical messages
//        simpMessagingTemplate.convertAndSendToUser(
//                String.valueOf(userId),
//                "/topic/public",
//                Collections.singletonMap("historical", true)
//        );
//
//        return historicalMessages;
//    }
    @MessageMapping("/fetch/history")
    public void fetchMessageHistory(@RequestParam Map<String, Object> params) {
        Long userId = Long.parseLong(params.get("userId").toString());
        List<Message> historicalMessages = messageService.getMessageByUser(userId);

        System.out.println("Historical Messages: " + historicalMessages);

        // Iterate through historical messages and send them individually
        for (Message historicalMessage : historicalMessages) {
            simpMessagingTemplate.convertAndSendToUser(
                    String.valueOf(userId),
                    "/topic/public",
                    new Message(historicalMessage)
            );
        }
    }
}

