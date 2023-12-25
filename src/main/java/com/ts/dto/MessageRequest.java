package com.ts.dto;

import com.ts.entity.CategoryName;

public class MessageRequest {

    private String message;
    private Long receiver;
    private String type;

    public MessageRequest() {
    }

    public MessageRequest(String message, Long receiver, String type) {
        this.message = message;
        this.receiver = receiver;
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getReceiver() {
        return receiver;
    }

    public void setReceiver(Long receiver) {
        this.receiver = receiver;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
